import chat2.MaHoa;

import java.util.Base64;

public class Main {
    public static void main(String[] args) {
        MaHoa mh = new MaHoa();
        String text = mh.encoded("Đình Vượng");
        System.out.println(text);
        System.out.println(mh.decoded(text));
    }
}
