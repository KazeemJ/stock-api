package stockapi.repo;

import stockapi.model.Symbol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SymbolRepository extends JpaRepository<Symbol, String> {

    List<Symbol> findBySymbol(String symbol);
}
