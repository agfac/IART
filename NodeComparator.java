import java.util.Comparator;

public class NodeComparator implements Comparator<Node>{

	@Override
	public int compare(Node o1, Node o2) {
		if(o1.getfValue()>o2.getfValue())
			return 1;
		else return -1;
	}

}