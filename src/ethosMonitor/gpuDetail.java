package ethosMonitor;

import javax.swing.*;

import org.jfree.chart.*;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.TickUnit;
import org.jfree.chart.axis.TickUnits;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.*;
import org.jfree.data.category.DefaultCategoryDataset;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import java.awt.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class gpuDetail extends JFrame{
	gpuDetail(int x,int y,ArrayList<String[]> rigtempDArray,int a[],ArrayList<String> profile) throws JsonParseException, JsonMappingException, IOException{
		JFrame frame = new JFrame("gpuDetail");
		frame.setBounds(x,y,500,500);
		
		EditDate getD = new EditDate();
		EditDate getDate = getD.getDate(profile);
		
		ArrayList<ArrayList<String>> gpuName = getDate.gpuName;
		ArrayList<String> rigNames = getDate.rigNames;
		
		getTempHist getHist = new getTempHist();
		getTempHist getTempHist = getHist.GetHist(profile, rigNames.get(a[0]), a[1]);
		
		ArrayList<String[]> gpuTempHist = new ArrayList<String[]>();
		gpuTempHist = getTempHist.gpuTempHist;
		
		ArrayList<Long> UnixTime = new ArrayList<Long>();
		ArrayList<Integer> tempList = new ArrayList<Integer>();
		ArrayList<Date> DateTime = new ArrayList<Date>();
		//Calendar cal = Calendar.getInstance();
		for(int i = 0;i < gpuTempHist.size();i++) {
			UnixTime.add((Long.valueOf(gpuTempHist.get(i)[0])));
			tempList.add(Integer.valueOf(gpuTempHist.get(i)[1]));
			DateTime.add(new Date(UnixTime.get(i)*1000));
		}
		
		
		ChartFactory.setChartTheme(StandardChartTheme.createLegacyTheme());
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for(int i = 0;i < gpuTempHist.size();i++) {
			dataset.addValue(tempList.get(i), "Temp", DateTime.get(i));
		}
		
		JFreeChart chart = ChartFactory.createLineChart("TempHist", "Time", "Temp", dataset, PlotOrientation.VERTICAL, true, false, false);
		CategoryPlot plot = chart.getCategoryPlot();
		plot.setOutlineVisible(false);
		NumberAxis yAxis = (NumberAxis)plot.getRangeAxis();
		//ValueAxis xAxis = plot.getRangeAxis();
		
		yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		yAxis.setLowerBound(30);
		yAxis.setUpperBound(90);
		
		
		
		
		//TickUnits tx = new TickUnits();
		TickUnits ty = new TickUnits();
		//TickUnit uniX = new NumberTickUnit(10000000);
		TickUnit uniY = new NumberTickUnit(10);
		//tx.add(uniX);
		ty.add(uniY);
		//xAxis.setStandardTickUnits(tx);
		yAxis.setStandardTickUnits(ty);
		
		
		ChartPanel panel = new ChartPanel(chart);
		
		frame.getContentPane().add(panel);
		
		frame.setVisible(true);
	}
}
