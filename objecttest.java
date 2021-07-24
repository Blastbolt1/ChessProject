import javafx.application.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.shape.Circle;
import javafx.event.*;
import javafx.geometry.*;
import java.util.Arrays;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.input.*;
import java.lang.Math;
import java.io.FileInputStream; 
import java.io.FileNotFoundException; 
import javafx.application.Application; 
import javafx.scene.Group; 
import javafx.scene.Scene; 
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;  
import javafx.stage.Stage; 

class chessBoard {
	int[][] board;
	
	public void printBoard() {
		board = new int[8][8];
		
		System.out.print("");
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				switch (board[j][i]) {
					case 1:
						System.out.print("1  ");
						break;               
					case 2:                  
						System.out.print("2  ");
						break;               
					case 3:                  
						System.out.print("3  ");
						break;               
					case 4:                  
						System.out.print("4  ");
						break;               
					case 5:                  
						System.out.print("5  ");
						break;               
					case 6:                  
						System.out.print("6  ");
						break;               
					case 7:                  
						System.out.print("7  ");
						break;               
					case 8:                  
						System.out.print("8  ");
						break;               
					case 9:                  
						System.out.print("9  ");
						break;
					case 10:              
						System.out.print("10 ");
						break;               
					case 11:                 
						System.out.print("11 ");
						break;               
					case 12:                 
						System.out.print("12 ");
						break;
					default:
						System.out.print("0  ");
				}
			}
			System.out.println("");
		}
	}
	
	public void initState() {
		board = new int[8][8];
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				board[j][i] = 1;
			}
		}
		
		printBoard();
	}
	
	
}

public class objecttest extends Application {
	public static void main (String[] args) {
		//Board.initState();
		
		launch(args);
	}
	
	public void start(Stage myStage) throws FileNotFoundException {
		myStage.setResizable(true);

		myStage.setTitle("Chess");

		GridPane rootNode = new GridPane();

		rootNode.setAlignment(Pos.CENTER);
		
		Pane pane = new Pane();

		Scene myScene = new Scene(pane, 850, 850);

		myStage.setScene(myScene);
		
		rootNode.setHgap(10);
		rootNode.setVgap(10);
		
		
		//Creating an image 
		Image WhitePawn = new Image(new FileInputStream("C:\\Users\\alex\\Documents\\CS\\Chess\\White\\WhitePawn.png"));
		Image WhiteRook = new Image(new FileInputStream("C:\\Users\\alex\\Documents\\CS\\Chess\\White\\WhiteRook.png"));
		Image WhiteKnight = new Image(new FileInputStream("C:\\Users\\alex\\Documents\\CS\\Chess\\White\\WhiteKnight.png"));
		Image WhiteBishop = new Image(new FileInputStream("C:\\Users\\alex\\Documents\\CS\\Chess\\White\\WhiteBishop.png"));
		Image WhiteQueen = new Image(new FileInputStream("C:\\Users\\alex\\Documents\\CS\\Chess\\White\\WhiteQueen.png"));
		Image WhiteKing = new Image(new FileInputStream("C:\\Users\\alex\\Documents\\CS\\Chess\\White\\WhiteKing.png"));
		
		Image BlackPawn = new Image(new FileInputStream("C:\\Users\\alex\\Documents\\CS\\Chess\\Black\\BlackPawn.png"));
		Image BlackRook = new Image(new FileInputStream("C:\\Users\\alex\\Documents\\CS\\Chess\\Black\\BlackRook.png"));
		Image BlackKnight = new Image(new FileInputStream("C:\\Users\\alex\\Documents\\CS\\Chess\\Black\\BlackKnight.png"));
		Image BlackBishop = new Image(new FileInputStream("C:\\Users\\alex\\Documents\\CS\\Chess\\Black\\BlackBishop.png"));
		Image BlackQueen = new Image(new FileInputStream("C:\\Users\\alex\\Documents\\CS\\Chess\\Black\\BlackQueen.png"));
		Image BlackKing = new Image(new FileInputStream("C:\\Users\\alex\\Documents\\CS\\Chess\\Black\\BlackKing.png"));
		
		Rectangle[][] chessBoard = new Rectangle[8][8];
		
		chessBoard myBoard = new chessBoard();
		myBoard.initState();
		myBoard.printBoard();
		
		Pawn[] pawns = new Pawn[8];
		
		ImageView[] WhitePawns = new ImageView[8];
		ImageView[] WhiteRooks = new ImageView[2];
		ImageView[] WhiteKnights = new ImageView[2];
		ImageView[] WhiteBishops = new ImageView[2];
		ImageView[] WhiteQueens = new ImageView[2];
		ImageView[] WhiteKings = new ImageView[2];
		
		boolean squareColour = true;
		double xOffset = 25;
		double yOffset = 25;
		double squareSize = 100;
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				chessBoard[i][j] = new Rectangle((j * squareSize) + xOffset, (i * squareSize) + yOffset, squareSize, squareSize);
				
				if (squareColour) {
					chessBoard[i][j].setFill(Color.rgb(255, 240, 200));
				} else {
					chessBoard[i][j].setFill(Color.rgb(70, 30, 0));
				}
				
				pane.getChildren().add(chessBoard[i][j]);
				squareColour =! squareColour;
			}
			squareColour =! squareColour;
		}
		
		
		
		
		/****************************************************************************
		PIECE REPRESENTATION AND GENERATION
		****************************************************************************/
		
		
		//Pawn Generation
		for (int i = 0; i < 8; i++) {
			pawns[i] = new Pawn();
			
			pawns[i].xCoordOld = i;
			pawns[i].yCoordOld = 6;
			pawns[i].firstMove = true;
			
			WhitePawns[i] = new ImageView(WhitePawn);
			
			//Setting the position of the image 
			WhitePawns[i].setX(xOffset + (i * squareSize) - (0.092 * squareSize)); 
			WhitePawns[i].setY(yOffset + (6 * squareSize) - (0.07 * squareSize)); 
			WhitePawns[i].setFitHeight(squareSize);
			WhitePawns[i].setFitWidth(1.185 * squareSize);
			
			pane.getChildren().add(WhitePawns[i]);
		}
		
		/****************************************
		Pawn Movement
		*****************************************/
		
		for (int b = 0; b < 8; b++) {
			
			int i = b;
			
			WhitePawns[i].setOnMousePressed(new EventHandler <MouseEvent>()
			{
				public void handle(MouseEvent event)
				{
					event.setDragDetect(true);
				}
			});
			
			WhitePawns[i].setOnMouseReleased(new EventHandler <MouseEvent>()
			{
				public void handle(MouseEvent event)
				{
					
					double xAxis = Math.floor((WhitePawns[i].getX() + (squareSize/2) - xOffset)/squareSize);
					double yAxis = Math.floor((WhitePawns[i].getY() + (squareSize/2) - yOffset)/squareSize);
					
					int xint = (int)xAxis;
					int yint = (int)yAxis;
					
					pawns[i].xCoordNew = xint;
					pawns[i].yCoordNew = yint;
					
					if (pawns[i].pawnMove()) {
						WhitePawns[i].setX(xOffset + (xAxis * squareSize) - (0.092 * squareSize));
						WhitePawns[i].setY(yOffset + (yAxis * squareSize) - (0.06 * squareSize));
						
						int x = (int)xAxis;
						int y = (int)yAxis;
						
						pawns[i].xCoordOld = x;
						pawns[i].yCoordOld = y;
						pawns[i].firstMove = false;
					} else {
						WhitePawns[i].setX(xOffset - (0.092 * squareSize) + (pawns[i].xCoordOld * squareSize));
						WhitePawns[i].setY(yOffset - (0.06 * squareSize) + (pawns[i].yCoordOld * squareSize));
					}
				}
			});
			
			WhitePawns[i].setOnMouseDragged(new EventHandler <MouseEvent>()
			{
				public void handle(MouseEvent event)
				{
					double mouseX = event.getX();
					double mouseY = event.getY();
					
					WhitePawns[i].setX(mouseX - (squareSize/2));
					WhitePawns[i].setY(mouseY - (squareSize/2));
					
					event.setDragDetect(false);
				}
			});
		}
		
		myStage.show();
	}
}