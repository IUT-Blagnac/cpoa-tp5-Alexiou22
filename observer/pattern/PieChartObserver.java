/**
 * 
 */
package observer.pattern;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Vector;

import javax.swing.JPanel;

import observer.CourseRecord;
import observer.LayoutConstants;

/**
 * @author Alexia
 *
 */
@SuppressWarnings("serial")
public class PieChartObserver extends JPanel implements Observer {

	/**
	 * 
	 */
	public PieChartObserver(CourseData data) {
		data.attach(this);
		this.courseData = data.getUpdate();
		this.setPreferredSize(new Dimension(2 * LayoutConstants.xOffset
				+ (LayoutConstants.barSpacing + LayoutConstants.barWidth)
				* this.courseData.size(), LayoutConstants.graphHeight + 2
				* LayoutConstants.yOffset));
		this.setBackground(Color.white);
	}
	
	/**
	 * Paint method
	 * 
	 * @param g
	 *            a Graphics object on which to paint
	 */
	public void paint(Graphics g, Integer[] data) {
		super.paint(g);
		int radius = 100;
		
		//first compute the total number of students
		double total = 0.0;
		for (int i = 0; i < data.length; i++) {
			total += data[i];
		}
		//if total == 0 nothing to draw
		if (total != 0) {
			double startAngle = 0.0;
			for (int i = 0; i < data.length; i++) {
				double ratio = (data[i] / total) * 360.0;
				//draw the arc
				g.setColor(LayoutConstants.courseColours[i%LayoutConstants.courseColours.length]);
				g.fillArc(LayoutConstants.xOffset, LayoutConstants.yOffset + 300, 2 * radius, 2 * radius, (int) startAngle, (int) ratio);
				startAngle += ratio;
			}
		}
	}

	/**
	 * Informs this observer that the observed CourseData object has changed
	 * 
	 * @param o
	 *            the observed CourseData object that has changed
	 */
	public void update(Observable o) {
		CourseData data = (CourseData) o;
		this.courseData = data.getUpdate();

		//this.setPreferredSize(new Dimension(2 * LayoutConstants.xOffset
				//+ (LayoutConstants.barSpacing + LayoutConstants.barWidth)
				//* this.courseData.size(), LayoutConstants.graphHeight + 2
				//* LayoutConstants.yOffset));
		this.revalidate();
		this.repaint();
	}

	private Vector<CourseRecord> courseData;

}
