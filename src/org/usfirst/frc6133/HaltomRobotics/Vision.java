package org.usfirst.frc6133.HaltomRobotics;

//import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import java.lang.Math;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Vision {
	
    public class TableData {
    	public int tl[], bl[], br[], tr[];
    	public void setTableData(double i_tl[], double i_bl[], double i_br[], double i_tr[]) {
    		tl[0] = (int)i_tl[0];
    		tl[1] = (int)i_tl[1];
    		bl[0] = (int)i_bl[0];
    		bl[1] = (int)i_bl[1];
    		br[0] = (int)i_br[0];
    		br[1] = (int)i_br[1];
    		tr[0] = (int)i_tr[0];
    		tr[1] = (int)i_tr[1];
    	}
    	
    	public TableData() {
    		tl = new int[2];
    		bl = new int[2];
    		br = new int[2];
    		tr = new int[2];
    	}
    	
    	public int findCenter() {
    		return (int)(((double)(tl[0] + bl[0])/2. + (double)(tr[0] + br[0])/2.)/2.);
    	}
    }
    
	public NetworkTable table;
    public TableData[] goals;
    public int goalsFound = 0;
    
    public Vision() {
    	goals = new TableData[3];
    	goals[0] = new TableData();
    	goals[1] = new TableData();
    	goals[2] = new TableData();
    	table = NetworkTable.getTable("Data");
    }
    
    public void getTableValues()
    {
    	double[] defaultValue = {0, 0};
    	goalsFound = (int)table.getNumber("goalsFound", 0);
    	//System.out.println("Goals Found: " + goalsFound);
    	for (int i = 0; i < 3; i++)
    	{
    		double[] i_tl = table.getNumberArray("tl"+i, defaultValue);
    		double[] i_bl = table.getNumberArray("bl"+i, defaultValue);
    		double[] i_br = table.getNumberArray("br"+i, defaultValue);
    		double[] i_tr = table.getNumberArray("tr"+i, defaultValue);
    		try {
    			goals[i].setTableData(i_tl, i_bl, i_br, i_tr);
    			//System.out.println("Distance: " + calcDistance(i));
    			//if (goals[i].tl[0] > 0)
        		//	System.out.println(goals[i].tl[0] + " "  + goals[i].bl[0] + " " + goals[i].br[0] + " " + goals[i].tr[0]);
    		} catch (java.lang.NullPointerException e) {
    			System.out.println("Error: " + e.toString());
    		}
    	}
    	if (goalsFound > 0)
    		SmartDashboard.putBoolean("goalFound", true);
    	else
    		SmartDashboard.putBoolean("goalFound", false);
    	//Timer.delay(.3);
    }
    
    public int findBestGoal()
    {
    	int result = -1;
    	for (int i = 0; i < 3; i++)
    	{
    		if (goals[i].bl[0] != 0 && Math.abs((goals[i].tr[1] - goals[i].tl[1]) / (goals[i].tr[0] - goals[i].tl[0] )) < .3)
    			result = i;
    			
    	}
    	
    	
    	return result;
    }
    
    public double calcWidth(int index)
    {
    	double result = 0;
    	/*double delta_x = goals[index].br[0] - goals[index].bl[0];
    	double delta_y = goals[index].br[1] - goals[index].tr[1];
    	double view_angle_theta = delta_y / (delta_x * .7f);
    	
    	result = (20)/ (delta_x/640. * Math.tan( Math.acos(view_angle_theta)));
    	*/
    	result = goals[index].br[0] - goals[index].bl[0];
    	
    	
    	return result;
    }
    
    public double calcAngle()
    {
    	/*TODO: The angle needs to be adjusted by extrapolating information.
    	 * We know that 102", the calcWidth() amount is about 120
    	 * We know that 165", the calcWidth() amount is about 71
    	 * 
    	 * We need to gather more data at different distances
    	 * For now this is returning a static amount.
    	 * 
    	 */
    	double width = calcWidth(findBestGoal());
    	System.out.println("CalcWidth: " + width);
    	if (width <= 72)
    		return 1350;
    	else if (width <= 79)
    		return 1400;
    	else if (width <= 84)
    		return 1450;
    	else if (width <= 89)
    		return 1500;
    	else if (width <= 99)
    		return 1600;
    	else if (width <= 115)
    		return 1700;
    	else
    		return SmartDashboard.getNumber("Angle", 1700.);
    }

}
