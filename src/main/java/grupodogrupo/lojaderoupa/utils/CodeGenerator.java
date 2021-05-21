package grupodogrupo.lojaderoupa.utils;

import java.time.Instant;
import java.util.Date;
import java.util.Random;

public class CodeGenerator {

    public static String gerarCodigo() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            sb.append(new Random().nextInt(10));
        }

        return sb.toString();
    }
}
