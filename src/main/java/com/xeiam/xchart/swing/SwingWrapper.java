/**
 * Copyright 2011-2012 Xeiam LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xeiam.xchart.swing;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.xeiam.xchart.Chart;

/**
 * @author timmolter
 */
public class SwingWrapper {

  private List<Chart> charts = new ArrayList<Chart>();
  private int numRows;
  private int numColumns;

  /**
   * Constructor
   * 
   * @param chart
   */
  public SwingWrapper(Chart chart) {

    this.charts.add(chart);
  }

  /**
   * Deprecated Constructor - use the one that takes a Collection! This will be removed in next version.
   * 
   * @param charts
   * @param numRows
   * @param numColumns
   */
  @Deprecated
  public SwingWrapper(Chart[] charts, int numRows, int numColumns) {

    for (int i = 0; i < charts.length; i++) {
      this.charts.add(charts[i]);
    }
    this.numRows = numRows;
    this.numColumns = numColumns;
  }

  /**
   * Constructor - The number of rows and columns will be calculated automatically
   * 
   * @param charts
   * @param numRows
   * @param numColumns
   */
  public SwingWrapper(List<Chart> charts) {

    this.charts = charts;

    this.numRows = (int) (Math.sqrt(charts.size()) + .5);
    this.numColumns = (int) ((double) charts.size() / this.numRows + 1);
  }

  /**
   * Constructor
   * 
   * @param charts
   * @param numRows - the number of rows
   * @param numColumns - the number of columns
   */
  public SwingWrapper(List<Chart> charts, int numRows, int numColumns) {

    this.charts = charts;
    this.numRows = numRows;
    this.numColumns = numColumns;
  }

  /**
   * Display the chart in a Swing JFrame
   */
  public void displayChart() {

    // Schedule a job for the event-dispatching thread:
    // creating and showing this application's GUI.
    javax.swing.SwingUtilities.invokeLater(new Runnable() {

      @Override
      public void run() {

        // Create and set up the window.
        JFrame frame = new JFrame("XChart");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        JPanel chartPanel = new ChartJPanel(charts.get(0));
        frame.getContentPane().add(chartPanel);

        // Display the window.
        frame.pack();
        frame.setVisible(true);
      }
    });
  }

  /**
   * Display the chart in a Swing JFrame
   */
  public void displayChartMatrix() {

    // Schedule a job for the event-dispatching thread:
    // creating and showing this application's GUI.
    javax.swing.SwingUtilities.invokeLater(new Runnable() {

      @Override
      public void run() {

        // Create and set up the window.
        JFrame frame = new JFrame("XChart");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new GridLayout(numRows, numColumns));

        for (Chart chart : charts) {
          if (chart != null) {
            JPanel chartPanel = new ChartJPanel(chart);
            frame.getContentPane().add(chartPanel);
          } else {
            JPanel chartPanel = new JPanel();
            frame.getContentPane().add(chartPanel);
          }

        }

        // Display the window.
        frame.pack();
        frame.setVisible(true);
      }
    });
  }

  private class ChartJPanel extends JPanel {

    private Chart chart;

    public ChartJPanel(Chart chart) {

      this.chart = chart;
    }

    @Override
    public void paint(Graphics g) {

      chart.paint((Graphics2D) g);
    }

    @Override
    public Dimension getPreferredSize() {

      return new Dimension(chart.getWidth(), chart.getHeight());
    }
  }
}
