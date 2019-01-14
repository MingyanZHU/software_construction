package helper;

import java.awt.Font;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.ui.VerticalAlignment;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class DataVisual {

  /**
   * 创建数据集合.
   *
   * @return dataSet
   */
  public static CategoryDataset createReadDataSet() {
    // 实例化DefaultCategoryDataset对象
    DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
    // 添加file1数据
    // Read Data
    //    dataSet.addValue(6554, "file1", "Buffer");
    //    dataSet.addValue(7252, "file1", "Reader");
    //    dataSet.addValue(55142, "file1", "Stream");
    dataSet.addValue(1033, "file1", "Buffer");
    dataSet.addValue(1354, "file1", "Writer");
    dataSet.addValue(330542, "file1", "Stream");
    // 添加file2数据
    dataSet.addValue(1112, "file2", "Buffer");
    dataSet.addValue(1354, "file2", "Writer");
    dataSet.addValue(328183, "file2", "Stream");
    // 添加file3数据
    dataSet.addValue(1276, "file3", "Buffer");
    dataSet.addValue(1559, "file3", "Writer");
    dataSet.addValue(401885, "file3", "Stream");
    // 添加file4数据
    dataSet.addValue(1016, "file4", "Buffer");
    dataSet.addValue(1286, "file4", "Writer");
    dataSet.addValue(326776, "file4", "Stream");
    return dataSet;
  }

  /**
   * 创建JFreeChart对象.
   *
   * @return chart
   */
  public static JFreeChart createChart() {
    StandardChartTheme standardChartTheme = new StandardChartTheme("CN"); //创建主题样式
    standardChartTheme.setExtraLargeFont(new Font("隶书", Font.BOLD, 20)); //设置标题字体
    standardChartTheme.setRegularFont(new Font("宋体", Font.PLAIN, 15)); //设置图例的字体
    standardChartTheme.setLargeFont(new Font("宋体", Font.PLAIN, 15)); //设置轴向的字体
    ChartFactory.setChartTheme(standardChartTheme);//设置主题样式
    // 通过ChartFactory创建JFreeChart
    JFreeChart chart = ChartFactory.createBarChart(
        //        "4个样例文件 3种不同读入方式的时间对比", //图表标题
        "4个样例图 3种不同的输出方式时间对比",
        "文件名称", //横轴标题
        "所需时间(ms)",//纵轴标题
        createReadDataSet(),//数据集合
        PlotOrientation.VERTICAL, //图表方向
        true,//是否显示图例标识
        false,//是否显示tooltips
        false);//是否支持超链接

    chart.getTitle().setFont(new Font("隶书", Font.BOLD, 25)); // 设置标题字体
    chart.getLegend().setItemFont(new Font("宋体", Font.PLAIN, 12)); // 设置图例类别字体
    chart.setBorderVisible(true); // 设置显示边框
    //TextTitle subTitle = new TextTitle("3种读入方式(Buffer, Reader, Stream)");//实例化TextTitle对象
    TextTitle subTitle = new TextTitle("3种输出方式(Buffer, Writer, Stream)");
    subTitle.setVerticalAlignment(VerticalAlignment.BOTTOM); //设置居中显示
    chart.addSubtitle(subTitle);//添加子标题
    CategoryPlot plot = chart.getCategoryPlot(); // 获取绘图区对象
    plot.setForegroundAlpha(0.8F);//设置绘图区前景色透明度
    plot.setBackgroundAlpha(0.5F);//设置绘图区背景色透明度
    CategoryAxis categoryAxis = plot.getDomainAxis();//获取坐标轴对象
    categoryAxis.setLabelFont(new Font("宋体", Font.PLAIN, 12));//设置坐标轴标题字体
    categoryAxis.setTickLabelFont(new Font("宋体", Font.PLAIN, 12));//设置坐标轴尺值字体
    categoryAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);// 设置坐标轴标题旋转角度
    ValueAxis valueAxis = plot.getRangeAxis();// 设置数据轴对象
    valueAxis.setLabelFont(new Font("宋体", Font.PLAIN, 12));
    return chart;
  }

  /**
   * 本地测试.
   */
  public static void main(String[] args) {
    ChartFrame cf = new ChartFrame("Test", createChart());
    cf.pack();
    cf.setVisible(true);
  }
}