import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UUIDTest {

  public static String[] getUUID(int number) {
    if (number < 1) {
      return null;
    }
    String[] ss = new String[number];
    for (int i = 0; i < number; i++) {
      ss[i] = getUUID();
    }
    return ss;
  }

  public static String getUUID() {
    UUID uuid = UUID.randomUUID();
    String str = uuid.toString();
    return str;
  }

  public static void main(String[] args) {
    String[] uuid = getUUID(100);
    for (int i = 0; i < uuid.length; i++) {
      System.out.println("uuid["+i+"]====="+uuid[i]);
    }
  }
}
