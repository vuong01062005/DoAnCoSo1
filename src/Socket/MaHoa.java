package Socket;

import java.util.Base64;
import java.nio.charset.StandardCharsets;

public class MaHoa {
    public String encoded(String text) {
        // Sử dụng StandardCharsets.UTF_8 để đảm bảo encoding đúng
        return Base64.getEncoder().encodeToString(text.getBytes(StandardCharsets.UTF_8));
    }

    public String decoded(String text) {
        try {
            // Giải mã Base64 và chuyển đổi lại thành chuỗi sử dụng UTF-8
            byte[] decodedBytes = Base64.getDecoder().decode(text);
            return new String(decodedBytes, StandardCharsets.UTF_8);
        } catch (IllegalArgumentException e) {
            // Xử lý ngoại lệ nếu chuỗi không hợp lệ
            System.err.println("Input string is not valid Base64 encoded data: " + e.getMessage());
            return null;
        }
    }
}
