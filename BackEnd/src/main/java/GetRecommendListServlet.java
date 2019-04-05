import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.util.Comparator;

@WebServlet("/recommend")
public class GetRecommendListServlet extends GetTopListServlet {
    @Override
    public void init() throws ServletException {
        comparator = new Comparator<JSONObject>() {
            private static final String KEY_NAME = "score";

            @Override
            public int compare(JSONObject a, JSONObject b) {
                double valA, valB;
                valA = (double) a.get(KEY_NAME);
                valB = (double) b.get(KEY_NAME);
                return Double.compare(valB, valA);
            }
        };
    }
}
