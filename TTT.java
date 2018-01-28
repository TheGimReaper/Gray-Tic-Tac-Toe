import javax.swing.*;

import org.omg.IOP.TAG_ALTERNATE_IIOP_ADDRESS;

import jdk.internal.dynalink.support.BottomGuardingDynamicLinker;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TTT{

	private static int[][] board = new int[3][3];
	
	public static void clear()
	{
		for (int rows = 0; rows < 3; rows++) {
			for (int cols = 0; cols < 3; cols++) {
				board[rows][cols] = 0;
			}
		}
	}

	public static void placeMark(int x, int y, int i) {
		if (board[x][y] == 0) {
			board[x][y] = i;
		}
	}

	public static boolean win() {
		int sum1 = 0;
		int sum2 = 0;
		boolean ret;
		//checks horizontal rows
		for(int i = 0; i < 3; i++) {
			if (Math.abs((board[1][i] + board[0][i] + board[2][i])) == 3) return true;
		}
		//checks vertical rows
		for(int i = 0; i < 3; i++) {
			if (Math.abs((board[i][0] + board[i][1] + board[i][2])) == 3) return true;
		}
		for(int i = 0; i < 3; i++) {
			sum1 += board[i][i];
			sum2 += board[i][2-i];
		}
		if(Math.abs(sum1) == 3 || Math.abs(sum2) == 3) return true;
		return false;
	}

	public static void printBoard() {
		String ret = "";
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				ret += board[i][j];
				ret += "\t";
			}
			ret += "\n";
		}
		System.out.println(ret);
	}

	private int p1_wins;
	private int p2_wins;
	private String p1_str;
	private String p2_str;
	private JFrame jf;
	private JPanel jp;
	private JPanel grid;
	private JLabel p1;
	private JLabel p2;
	private JLabel header;
	private static JLabel turn;
	private static JLabel outcome;
	private static String player;
	private static JButton newGame;
	private static ArrayList<JButton> buttons = new ArrayList<JButton>();

	public TTT() {
		jf = new JFrame("Tic Tac Toe");
		jp = new JPanel();
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		grid = new JPanel();
		p1_wins = 0;
		grid.setLayout(new GridLayout(3,3));
		jf.setSize(500, 500);
		jp.setSize(300, 400);
		grid.setSize(400,400);
		jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));
		p2_wins = 0;
		p1_str = "Player 1: " + Integer.toString(p1_wins);
		p2_str = "Player 2: " + Integer.toString(p2_wins);
		for (int rows = 0; rows < 3; rows++) {
			for (int cols = 0; cols < 3; cols++) {
				JButton temp = new JButton(" ");
				temp.setBackground(Color.white);
				//jb.setPreferredSize(new Dimension(50, 50));
				temp.addActionListener(
					new ActionListener(){
					
						@Override
						public void actionPerformed(ActionEvent e) {
							int index = buttons.indexOf(e.getSource());
							if(player == "Player 1") {
								buttons.get(index).setText("X");
								placeMark(index/3, index%3, 1);
								buttons.get(index).setEnabled(false);
								switchTurns();
							}
							else {
								placeMark(index/3, index%3, -1);
								buttons.get(index).setText("O");
								buttons.get(index).setEnabled(false);
								switchTurns();
							}
							if(win()) {
								if (player == "Player 2") {
									p1_wins++;
									p1_str = "Player 1: " + Integer.toString(p1_wins);
									p1.setText(p1_str);
									outcome.setText("Outcome: Player 1 Wins!");
								} 
								else {
									p2_wins++;
									p2_str = "Player 2: " + Integer.toString(p2_wins);
									p2.setText(p2_str);
									outcome.setText("Outcome: Player 2 Wins!");
								}
								endGame();
							}
							else {
								if(boardFilled()) { 
									outcome.setText("Outcome: Draw.");
									endGame();
								}
							}
							printBoard();
							System.out.println(win());
							
						}
					}
				);
				buttons.add(temp);
				grid.add(buttons.get(rows * 3 + cols));
			}
		}	

		newGame = new JButton("New Game");
		newGame.setBackground(Color.WHITE);
		newGame.addActionListener(
			new ActionListener(){
			
				@Override
				public void actionPerformed(ActionEvent e) {
					resetBoard();
				}
			}
		);
		p1 = new JLabel(p1_str, JLabel.LEFT);
		p2 = new JLabel(p2_str, JLabel.LEFT);
		player = "Player 1";
		turn = new JLabel(("Turn: " + player), JLabel.LEFT);
		outcome = new JLabel("Outcome: ", JLabel.LEFT);
		header = new JLabel("Tic Tac Toe", JLabel.LEFT);
		p1.setMinimumSize(new Dimension(100,30));
		p1.setPreferredSize(new Dimension(100,30));
		p1.setMaximumSize(new Dimension(100, 30));
		p2.setMinimumSize(new Dimension(100,30));
		p2.setPreferredSize(new Dimension(100,30));
		p2.setMaximumSize(new Dimension(100, 30));
		turn.setMinimumSize(new Dimension(100,30));
		turn.setPreferredSize(new Dimension(100,30));
		turn.setMaximumSize(new Dimension(100, 30));
		outcome.setMinimumSize(new Dimension(200,30));
		outcome.setPreferredSize(new Dimension(200,30));
		outcome.setMaximumSize(new Dimension(200, 30));
		header.setMinimumSize(new Dimension(200,50));
		header.setPreferredSize(new Dimension(200,50));
		header.setMaximumSize(new Dimension(200, 50));
		p1.setForeground(Color.white);
		p2.setForeground(Color.white);
		turn.setForeground(Color.white);
		outcome.setForeground(Color.white);
		header.setForeground(Color.WHITE);
		header.setFont(new Font("Arial", Font.BOLD, 20));
		jp.add(header);
		jp.add(Box.createHorizontalGlue());
		jp.add(grid);
		jp.add(p1);
		jp.add(p2);
		jp.add(turn);
		jp.add(outcome);
		jf.add(jp);
		jp.add(newGame);
		jp.add(new JLabel(" "));
		jp.setBackground(Color.darkGray);
		jf.setVisible(true);
	}

	public static void play()
	{
		newGame.setEnabled(false);
		resetBoard();
		clear();
	}

	public static boolean boardFilled() {
		for(int i = 0; i < buttons.size(); i++) {
			if(buttons.get(i).isEnabled() == true) return false;
		}
		return true;
	}

	public static void switchTurns() {
		if(player == "Player 1") {
			player = "Player 2";
		}
		else {
			player = "Player 1";
		}
		turn.setText("Turn: " + player);
	}

	public static void resetBoard() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				buttons.get(i*3 + j).setText(" ");
				buttons.get(i*3 + j).setEnabled(true);
			}
		}
		outcome.setText("Outcome: ");
		clear();
	}

	public static void endGame() {
		for (int i = 0; i <buttons.size(); i++)
		{
			buttons.get(i).setEnabled(false);
		}
		newGame.setEnabled(true);
	}

	public static void main(String[] args) {
		TTT ttt = new TTT();
		play();
	}
}