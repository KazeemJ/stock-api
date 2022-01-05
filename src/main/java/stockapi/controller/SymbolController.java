package stockapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import stockapi.model.Symbol;
import stockapi.service.SymbolService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("symbols")
public class SymbolController {

    private static final Logger log = LoggerFactory.getLogger(SymbolController.class);

    @Autowired
    private SymbolService service;


    @GetMapping()
    public List<Symbol> list() {
        return service.listAll();
    }

    @GetMapping("/{symbol}")
    public ResponseEntity<Symbol> get(@PathVariable String symbol) {
        try {

            Symbol alphaSymbol = service.getSymbolAlphaData(symbol);
            log.info("alpha data: {}" + alphaSymbol);

            List<Symbol> symbolRecords = service.getBySymbolName(symbol);
            if(symbolRecords.size() > 0){
                symbolRecords.get(0).setOpen(alphaSymbol.getOpen());
                symbolRecords.get(0).setClose(alphaSymbol.getClose());
                service.save(symbolRecords.get(0));
            }else{
                service.save(alphaSymbol);
            }
            return new ResponseEntity<Symbol>(alphaSymbol, HttpStatus.OK);

        } catch (NoSuchElementException e) {
            return new ResponseEntity<Symbol>(HttpStatus.NOT_FOUND);
        } catch (JsonProcessingException | ResponseStatusException e){
            log.error("Symbol not found: {}", e.getMessage());
            return new ResponseEntity<Symbol>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id){
        service.delete(id);
    }



}
