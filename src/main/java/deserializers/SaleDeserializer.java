package deserializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.entities.Sale;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

public class SaleDeserializer implements Deserializer<Sale> {
    @Override
    public Sale deserialize(String topic, byte[] sale) {
        try {
            return new ObjectMapper().readValue(sale, Sale.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
