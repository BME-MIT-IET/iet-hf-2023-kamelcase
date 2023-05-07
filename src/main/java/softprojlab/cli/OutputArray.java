package softprojlab.cli;

import java.util.ArrayList;

public class OutputArray<T> extends ArrayList<T> {
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.size(); i++) {
            sb.append(this.get(i)).append(i < this.size() - 1 ? ", " : "");
        }

        return sb.toString();
    }
}
