package gaiax.lcm.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import gaiax.lcm.model.DashboardServiceResponse;
import gaiax.lcm.model.DatasetResponse;
import gaiax.lcm.model.Statistics;
import gaiax.lcm.model.TransactionResponse;
import gaiax.lcm.util.ProxyCall;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/lcm-service")
@ApiOperation("Dashboard Service")
public class DashboardController {

    private final WebClient demoSrv;

    @Autowired
    public DashboardController(WebClient demoSrv) {
        this.demoSrv = demoSrv;
    }

    @ApiOperation("Get Transactions")
    @GetMapping("/transactions")
    public ResponseEntity<TransactionResponse> getTransactions(HttpServletRequest request) {
        ResponseEntity<Map<String, ?>> re = ProxyCall.doGet(demoSrv, request);
        if (re == null) return ResponseEntity.badRequest().build();
        Map<String, ?> rsObj = re.getBody();
        if (rsObj == null || rsObj.isEmpty()) return ResponseEntity.badRequest().build();
        ObjectMapper mapper = new ObjectMapper();
        TransactionResponse rs = mapper.convertValue(rsObj, TransactionResponse.class);
        return ResponseEntity.ok(rs);
    }

    @ApiOperation("Get Datasets")
    @GetMapping("/datasets")
    public ResponseEntity<DatasetResponse> getDatasets(HttpServletRequest request) {
        ResponseEntity<Map<String, ?>> re = ProxyCall.doGet(demoSrv, request);
        if (re == null) return ResponseEntity.badRequest().build();
        Map<String, ?> rsObj = re.getBody();
        if (rsObj == null || rsObj.isEmpty()) return ResponseEntity.badRequest().build();
        ObjectMapper mapper = new ObjectMapper();
        DatasetResponse rs = mapper.convertValue(rsObj, DatasetResponse.class);
        return ResponseEntity.ok(rs);
    }

    @ApiOperation("Get Solution Packages")
    @GetMapping("/sp")
    public ResponseEntity<DashboardServiceResponse> getSP(HttpServletRequest request) {
        ResponseEntity<Map<String, ?>> re = ProxyCall.doGet(demoSrv, request);
        if (re == null) return ResponseEntity.badRequest().build();
        Map<String, ?> rsObj = re.getBody();
        if (rsObj == null || rsObj.isEmpty()) return ResponseEntity.badRequest().build();
        ObjectMapper mapper = new ObjectMapper();
        DashboardServiceResponse rs = mapper.convertValue(rsObj, DashboardServiceResponse.class);
        return ResponseEntity.ok(rs);
    }

    @ApiOperation("Get Services")
    @GetMapping("/services")
    public ResponseEntity<DashboardServiceResponse> getServices(HttpServletRequest request) {
        ResponseEntity<Map<String, ?>> re = ProxyCall.doGet(demoSrv, request);
        if (re == null) return ResponseEntity.badRequest().build();
        Map<String, ?> rsObj = re.getBody();
        if (rsObj == null || rsObj.isEmpty()) return ResponseEntity.badRequest().build();
        ObjectMapper mapper = new ObjectMapper();
        DashboardServiceResponse rs = mapper.convertValue(rsObj, DashboardServiceResponse.class);
        return ResponseEntity.ok(rs);
    }

    @ApiOperation("Get Statistics")
    @GetMapping("/statistics")
    public ResponseEntity<Statistics> getStatistics(HttpServletRequest request) {
        ResponseEntity<Map<String, ?>> re = ProxyCall.doGet(demoSrv, request);
        if (re == null) return ResponseEntity.badRequest().build();
        Map<String, ?> rsObj = re.getBody();
        if (rsObj == null || rsObj.isEmpty()) return ResponseEntity.badRequest().build();
        ObjectMapper mapper = new ObjectMapper();
        Statistics plots = mapper.convertValue(rsObj, Statistics.class);
        return ResponseEntity.ok(plots);
    }

}
