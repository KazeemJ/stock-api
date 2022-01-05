package stockapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import stockapi.model.Symbol;
import stockapi.repo.SymbolRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
@Transactional
@PropertySource("classpath:application.properties")
public class SymbolService {

    private static final Logger log = LoggerFactory.getLogger(SymbolService.class);

    @Autowired
    private SymbolRepository repo;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${alphavantage.url}")
    private String alphaurl;

    public List<Symbol> listAll() {
        return repo.findAll();
    }

    public void save(Symbol product) {
        repo.save(product);
    }

    public Symbol get(String id) {
        return repo.findById(id).get();
    }

    public List<Symbol> getBySymbolName(String symbol){
        return repo.findBySymbol(symbol);
    }

    public void delete(String id) {
        repo.deleteById(id);
    }

    public Symbol getSymbolAlphaData(String symbol) throws JsonProcessingException {
        JsonNode root = null;
            ResponseEntity<String> response
                    = restTemplate.getForEntity(alphaurl.replace("{symbol}",symbol),String.class);
            root = objectMapper.readTree(response.getBody());


        if(!root.path("Error Message").isMissingNode()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Symbol not found", null);
        }

        LocalDateTime ldt = LocalDateTime.now();
        String localTime = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH).format(ldt);
        JsonNode rootNode = root.path("Time Series (Daily)").path(localTime);

        String openString = rootNode.path("1. open").toString();
        String closeString = rootNode.path("4. close").toString();
        Float open = Float.parseFloat(openString.substring(1,openString.length()-1));
        Float close = Float.parseFloat(closeString.substring(1,closeString.length()-1));
        Symbol alphaSymbol = new Symbol(UUID.randomUUID().toString(), symbol,open,close);

        log.info("Symbol: {} Open: {} Close: {}", symbol,open,close);
        return alphaSymbol;

    }
}
