package com.mathgame.math;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import com.mathgame.database.MySQLAccess;


public class Items implements ItemListener
{
	MathGame mathGame;
	JLabel correct;
	JLabel correction;
	JCheckBox freeStyle;
	JCheckBox database;
	boolean useDatabase;
	Double check;
	MySQLAccess sql;
	
	
	public Items(MathGame view){
		this.mathGame = view;
		correct = view.correct;
		correction = view.correction;
		freeStyle = view.freeStyle;
		database = view.database;
		useDatabase = view.useDatabase;
		check = view.check;
		sql = view.sql;
		
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		Object source = e.getItemSelectable();
		if(source.equals(freeStyle))
		{
			if(e.getStateChange() == ItemEvent.SELECTED)
			{
				mathGame.setPractice(true);
				correct.setText("Answer");
				correct.setBorder(new LineBorder(Color.black));
				correction.setText("");
			}
			
			if(e.getStateChange() == ItemEvent.DESELECTED)
			{
				mathGame.setPractice(false); 
				correct.setText(String.valueOf(check));
				correct.setBorder(new LineBorder(Color.black));
				correction.setText("");
			}
		}
		if(source.equals(database))
		{
			if(e.getStateChange() == ItemEvent.SELECTED)
			{
				useDatabase = true;
				try {
					//if(sql.connect())
					{
						sql.connect();
						mathGame.setDatabase(useDatabase);
						Calc calc = new Calc(mathGame);
						calc.randomize();
						correction.setText("Connected to database");
					}
					//else
					//{
					//	correction.setText("Could not connect to database");
					//	mathGame.setDatabase(false);
					//}
						
				} catch (Exception e1) {
					correction.setText("Could not connect to database ");//e1.getMessage());
					//use this for debugging server connection
					//correction.setText("Db err: " + sql.sqlError);
					//mathGame.error();
					e1.printStackTrace();
					mathGame.setDatabase(false);

				}
			}
			
			if(e.getStateChange() == ItemEvent.DESELECTED)
			{
				useDatabase = false;
				try{
				sql.close();
				mathGame.setDatabase(useDatabase);
				}
				catch(Exception e1){
					e1.printStackTrace();
					
				}
						
			}
			
			
			//System.out.println("ITEM USEDATABASE: " + useDatabase);
			
		}
	
	}//itemStateChanged function
	
}//Items subclass


		
