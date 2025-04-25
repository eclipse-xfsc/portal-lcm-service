package gaiax.lcm.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import gaiax.lcm.model.*;
import gaiax.lcm.model.sd.ServiceDescription;
import gaiax.lcm.util.ProxyCall;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
@Slf4j
@RestController
@RequestMapping("/api/lcm-service")
@Api(tags = "LCM Service")
public class LcmController {

    private final WebClient demoSrv;
    private final ObjectMapper mapper;

    @Autowired
    public LcmController(WebClient demoSrv) {
        this.demoSrv = demoSrv;
        this.mapper = new ObjectMapper();
    }

    @ApiOperation("Returns the Service Overview Information.")
    @GetMapping(value = "/service/overview")
    public ResponseEntity<?> serviceOverview(HttpServletRequest request, @RequestParam("serviceId") String sid) {
        ResponseEntity<Object> rs = ProxyCall.doGet(demoSrv, request);
        ServicesResponse servicesResponse = mapper.convertValue(rs.getBody(), ServicesResponse.class);

        for (ServiceOverviewDto service : servicesResponse.getServices()) {
            ServiceDescription[] serviceDescriptions = loadServiceDescriptorForService(service.getServiceName());

            service.setApplicableLcm(toLcm(Arrays.stream(serviceDescriptions).flatMap(x -> Arrays.stream(x.getDeployments())).collect(Collectors.toList())));
        }
        return ResponseEntity.ok(servicesResponse);
    }

    @ApiOperation("Retrieve LCM Configuration")
    @PostMapping(value = "/service/configuration")
    public ResponseEntity<?> lcmConfiguration(HttpServletRequest request, @RequestBody GetServicesConfigRq rq) {
        ResponseEntity<Object> rs = ProxyCall.doPost(demoSrv, request, rq);
        ServicesConfigResponse servicesResponse = mapper.convertValue(rs.getBody(), ServicesConfigResponse.class);
        return ResponseEntity.ok(servicesResponse);
    }

    @ApiOperation("Update configuration")
    @PostMapping(value = "/service")
    public ResponseEntity<?> updateConfiguration(HttpServletRequest request, @RequestBody UpdateConfigRq rq) {
        return ProxyCall.doPost(demoSrv, request, rq);
    }

    @ApiOperation("Download configuration template.")
    @PostMapping(value = "/templates")
    public ResponseEntity<?> templates(@RequestHeader HttpHeaders headers, HttpServletRequest request, @RequestBody GetServicesConfigRq rq) throws IOException {
        Flux<DataBuffer> flux = ProxyCall.doPostStream(demoSrv, request, rq);
        InputStream inputStream = flux.map(b -> b.asInputStream(true))
                .reduce(SequenceInputStream::new)
                .block(Duration.ofSeconds(10));
        if (inputStream == null) return ResponseEntity.badRequest().body("Input Stream is null");
        long contentLength = new InputStreamResource(inputStream).contentLength();
        Flux<DataBuffer> flux2 = ProxyCall.doPostStream(demoSrv, request, rq);
        InputStream inputStream2 = flux2.map(b -> b.asInputStream(true))
                .reduce(SequenceInputStream::new)
                .block(Duration.ofSeconds(10));
        if (inputStream2 == null) return ResponseEntity.badRequest().body("Input Stream is null");
        InputStreamResource resource = new InputStreamResource(inputStream2);
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(contentLength)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @ApiOperation("Upload Configuration, this endpoints expects the template downloaded from /template filled with actual values.")
    @PostMapping(value = "/configuration", consumes = "multipart/form-data")
    public ResponseEntity<?> createServices(HttpServletRequest request,
                                            @Parameter(
                                                    description = "File to be uploaded",
                                                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)) @RequestPart(value = "file") MultipartFile file) {
        return ProxyCall.doPostFile(demoSrv, request, file);
    }

    @ApiOperation("Delete LCM Service.")
    @DeleteMapping(value = "/service/{id}")
    public ResponseEntity<?> deleteService(@PathVariable String id) {
        DeleteResponse response = new DeleteResponse(true);

        return ResponseEntity.ok(response);
    }

    @ApiOperation("Retrieve logs for LCM Service")
    @GetMapping(value = "/service/{id}/logs")
    public ResponseEntity<?> getLogs(HttpServletRequest request, @PathVariable String id) {
        try {
            log.info("getLogs: {}", request.getRequestURI());
            Flux<DataBuffer> flux = ProxyCall.doGetStream(demoSrv, request);
            InputStream inputStream = flux.map(b -> b.asInputStream(true))
                    .reduce(SequenceInputStream::new)
                    .block(Duration.ofSeconds(10));
            if (inputStream == null) return ResponseEntity.badRequest().body("Input Stream is null");
            long contentLength = new InputStreamResource(inputStream).contentLength();
            Flux<DataBuffer> flux2 = ProxyCall.doGetStream(demoSrv, request);
            InputStream inputStream2 = flux2.map(b -> b.asInputStream(true))
                    .reduce(SequenceInputStream::new)
                    .block(Duration.ofSeconds(10));
            if (inputStream2 == null) return ResponseEntity.badRequest().body("Input Stream is null");
            InputStreamResource resource = new InputStreamResource(inputStream2);
            return ResponseEntity.ok()
                    .contentLength(contentLength)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("error occurred: {}", e.getMessage());
            throw new RuntimeException();
        }
    }

    private ServiceOverviewDto.ApplicableLcm[] toLcm(List<String> applicableServices) {
        return applicableServices.stream().map(x -> new ServiceOverviewDto.ApplicableLcm(x, x, x))
                .collect(Collectors.toList()).toArray(new ServiceOverviewDto.ApplicableLcm[0]);
    }

    private ServiceDescription[] loadServiceDescriptorForService(String serviceId) {
        final WebClient.RequestHeadersSpec<?> callBuilder = demoSrv
                .get()
                .uri(builder ->
                        builder.path("/api/sd-service/services/" + serviceId).build());
        ResponseEntity<ServiceDescription[]> response = callBuilder.retrieve().toEntity(ServiceDescription[].class).block();
        return response.getBody();
    }
}
