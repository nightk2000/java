package hout;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.IRStatistics;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.eval.RecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.GenericRecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.model.jdbc.ReloadFromJDBCDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.JDBCDataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.cf.taste.similarity.precompute.example.GroupLensDataModel;

import com.mysql.cj.jdbc.MysqlDataSource;

public class MahoutApi {

	private DataModel dataModel = null;
	
	private Recommender userRecommender = null;
	
	private GenericItemBasedRecommender itemRecommender = null;
	
	public void Init(String file) {
		dataModel = fileDataModel(file);
		this.InitRecommender();
	}
	
	public void Init(String url, String database, String username, String password) {
		dataModel = jdbcDataModel(url, database, username, password);
		this.InitRecommender();
	}
	
	private void InitRecommender() {
		if(dataModel==null)
			return;
		try{
			{
		    	//计算相似度，相似度算法有很多种，欧几里得、皮尔逊等等。
		    	UserSimilarity similarity = new PearsonCorrelationSimilarity(dataModel);
		        //计算最近邻域，邻居有两种算法，基于固定数量的邻居和基于相似度的邻居，这里使用基于固定数量的邻居
		        UserNeighborhood userNeighborhood = new NearestNUserNeighborhood(100, similarity, dataModel);
		        //构建推荐器，协同过滤推荐有两种，分别是基于用户的和基于物品的，这里使用基于用户的协同过滤推荐
		        userRecommender = new GenericUserBasedRecommender(dataModel, userNeighborhood, similarity);
			}
			{
		        //计算相似度，相似度算法有很多种，欧几里得、皮尔逊等等。
		        ItemSimilarity itemSimilarity = new PearsonCorrelationSimilarity(dataModel);
		        //构建推荐器，协同过滤推荐有两种，分别是基于用户的和基于物品的，这里使用基于物品的协同过滤推荐
		        itemRecommender = new GenericItemBasedRecommender(dataModel, itemSimilarity);
			}
		}catch (Exception e) {

		}
	}
	
	/**
	 * 基于用户历史习惯为 userId 推荐 amount 个相应物品
	 * @param userId
	 * @param amount
	 * @return
	 */
	public final synchronized List<RecommendedItem> baseUser(long userId, int amount) {
		List<RecommendedItem> resultlist = Collections.emptyList();
		if( userRecommender==null )
			return resultlist;
		try {
			resultlist = userRecommender.recommend(userId, amount);
	        System.out.println("使用基于用户的协同过滤算法");
	        for (RecommendedItem recommendedItem : resultlist) {
	            System.out.println(recommendedItem);
	        }
		}catch (Exception e) {
		}
        return resultlist;
	}

	/**
	 * 基于用户浏览的item 推荐 amount 个相应物品
	 * @param userId
	 * @param itemId
	 * @param amount
	 * @return
	 */
	public final synchronized List<RecommendedItem> baseItem(long userId, long itemId, int amount){
		List<RecommendedItem> resultlist = Collections.emptyList();
		if(itemRecommender==null)
			return resultlist;
		try {
	        List<RecommendedItem> recommendedItemList = itemRecommender.recommendedBecause(userId, itemId, amount);
	        System.out.println("使用基于物品的协同过滤算法");
	        for (RecommendedItem recommendedItem : recommendedItemList) {
	            System.out.println(recommendedItem);
	        }
		}catch (Exception e) {
		}
		return resultlist;
	}
	
	/**
	 * 推荐模型评估
	 * 用70%的数据用作训练，剩下的30%用来测试
	 * 最后得出的评估值越小，说明推荐结果越好
	 */
	public final synchronized void modelAssess() {
        //推荐评估，使用均方根
        //RecommenderEvaluator evaluator = new RMSRecommenderEvaluator();
        //推荐评估，使用平均差值
        RecommenderEvaluator evaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();
        
        RecommenderBuilder builder = new RecommenderBuilder() {
            public Recommender buildRecommender(DataModel dataModel) throws TasteException {
                UserSimilarity similarity = new PearsonCorrelationSimilarity(dataModel);
                UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, dataModel);
                return new GenericUserBasedRecommender(dataModel, neighborhood, similarity);
            	//return userRecommender;
            	//return itemRecommender;
            }
        };
        
        try {
	        // 用70%的数据用作训练，剩下的30%用来测试
	        double score = evaluator.evaluate(builder, null, dataModel, 0.7, 1.0);
	        //最后得出的评估值越小，说明推荐结果越好
	        System.out.println(score);
        }catch (Exception e) {
        	e.printStackTrace();
		}
    }
	
	/**
	 * 获取推荐的准确率和召回率
	 */
	public final synchronized void modelAccuracy() {
        RecommenderIRStatsEvaluator statsEvaluator = new GenericRecommenderIRStatsEvaluator();
        RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
            public Recommender buildRecommender(DataModel model) throws TasteException {
                // UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
                // UserNeighborhood neighborhood = new NearestNUserNeighborhood(4, similarity, model);
                // return new GenericUserBasedRecommender(model, neighborhood, similarity);
            	return userRecommender;
            	//return itemRecommender;
            }
        };
        try {
	        // 计算推荐4个结果时的查准率和召回率
	        //使用评估器，并设定评估期的参数
	        //4表示"precision and recall at 4"即相当于推荐top4，然后在top-4的推荐上计算准确率和召回率
	        IRStatistics stats = statsEvaluator.evaluate(recommenderBuilder, null, dataModel, null, 4, GenericRecommenderIRStatsEvaluator.CHOOSE_THRESHOLD, 1.0);
	        System.out.println(stats.getPrecision());
	        System.out.println(stats.getRecall());
        }catch (Exception e) {
		}
	}
	

	private DataModel jdbcDataModel(String url, String database, String username, String password) {
    	try {
	    	MysqlDataSource mds = new MysqlDataSource();
	    	mds.setServerName("mahout");
	    	mds.setUrl(url);
	    	mds.setDatabaseName(database);
	    	mds.setUser(username);
	    	mds.setPassword(password);
	    	mds.setCachePrepStmts(true);
	    	mds.setCacheResultSetMetadata(true);
	    	mds.setAlwaysSendSetIsolation(false);
	    	mds.setElideSetAutoCommits(true);
	    	
	    	//                                               数据源，表明，用户ID，物品ID，权重（购买数量、评分等），记录时间
	    	JDBCDataModel dataModel = new MySQLJDBCDataModel(mds, "mahout", "user_id", "item_id", "num", "time");
	    	// 将数据加载至内存中进行计算
	    	ReloadFromJDBCDataModel rfdm = new ReloadFromJDBCDataModel(dataModel);
	    	return rfdm;
    	}catch (Exception e) {
		}
    	return null;
	}
	
	private DataModel fileDataModel(String dir) {
    	try {
        	//准备数据 这里是电影评分数据
	    	File file = new File(dir);
	        //将数据加载到内存中，GroupLensDataModel是针对开放电影评论数据的
	    	DataModel dataModel = new GroupLensDataModel(file);
	    	return dataModel;
    	}catch (Exception e) {

		}
    	return null;
	}
}
