import javax.xml.transform.Result;
import java.util.concurrent.Callable;

/**
 * Created by Developer on 21/03/2016.
 */
public class MatrixTask implements Callable<ResultHelper> {
    private static Integer m1[][];
    private static Integer m2[][];
    private int i;
    private int j;

    MatrixTask(Integer[][] m1, Integer[][] m2, int i, int j) {
        this.m1 = m1;
        this.m2 = m2;
        this.i = i;
        this.j = j;
    }

    @Override
    public ResultHelper call() throws Exception {
        Integer result = 0;

        for(int k = 0; k < Main.size; k++) {
                result += m1[i][k] * m2[k][j];
        }

        ResultHelper rs = new ResultHelper(i, j, result);
        return rs;
    }
}
