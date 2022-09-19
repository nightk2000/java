package hout;

import java.io.IOException;

import org.apache.mahout.cf.taste.common.TasteException;

/**
 * Hello world!
 *
 */
public class App 
{
	// https://blog.csdn.net/Jason_Nevermind/article/details/123982764
	// https://github.com/apache/mahout
	// https://mahout.apache.org/general/downloads
	
	// https://grouplens.org/datasets/movielens/
	
	/**
	 * 用户ID::商品ID::购买数量
	 */
	private static final String dir = "E:\\ml-10M100K\\ratings.dat";
	
	private static final String url = "jdbc:mysql://127.0.0.1:3306/draw";
	private static final String db  = "draw";
	private static final String user= "root";
	private static final String pass= "root";
	
	private static MahoutApi api = new MahoutApi();
	
    public static void main( String[] args ) throws IOException, TasteException
    {
        api.Init(dir);
        api.modelAssess();
    }
    
    
}
