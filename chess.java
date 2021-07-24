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

public class chess extends Application {
	public static void main (String[] args) {
		
		launch(args);
	}
	
	int[][] board = new int[8][8];
	
	public void printBoard() {
		System.out.println("");
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
	
	public void setSquare(int x, int y, int value) {
		board[x][y] = value;
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
		
		Piece master = new Piece();
		master.gameBoard = board;
		
		Pawn[] pawns = new Pawn[16];
		Rook[] rooks = new Rook[4];
		Knight[] knights = new Knight[4];
		Bishop[] bishops = new Bishop[4];
		Queen[] queens = new Queen[2];
		King[] kings = new King[2];
		
		ImageView[] WhitePawns = new ImageView[16];
		ImageView[] WhiteRooks = new ImageView[4];
		ImageView[] WhiteKnights = new ImageView[4];
		ImageView[] WhiteBishops = new ImageView[4];
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
					chessBoard[i][j].setFill(Color.rgb(221, 227, 237));
				} else {
					chessBoard[i][j].setFill(Color.rgb(145, 164, 194));
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
		for (int i = 0; i < 16; i++) {
			if (i < 8) {
				pawns[i] = new Pawn();
			
				pawns[i].xCoordOld = i;
				pawns[i].yCoordOld = 6;
				pawns[i].firstMove = true;
				pawns[i].colour = true;
				
				master.gameBoard[i][6] = 1;
				
				WhitePawns[i] = new ImageView(WhitePawn);
			} else {
				pawns[i] = new Pawn();
			
				pawns[i].xCoordOld = i - 8;
				pawns[i].yCoordOld = 1;
				pawns[i].firstMove = true;
				pawns[i].colour = false;
			
				master.gameBoard[pawns[i].xCoordOld][pawns[i].yCoordOld] = 7;
			
				WhitePawns[i] = new ImageView(BlackPawn);
			}
			
			
			//Setting the position of the image 
			WhitePawns[i].setX(xOffset + (pawns[i].xCoordOld * squareSize) - (0.092 * squareSize)); 
			WhitePawns[i].setY(yOffset + (pawns[i].yCoordOld * squareSize) - (0.07 * squareSize)); 
			WhitePawns[i].setFitHeight(squareSize);
			WhitePawns[i].setFitWidth(1.185 * squareSize);
			
			pane.getChildren().add(WhitePawns[i]);
		}
		
		//Rook Generation
		for (int i = 0; i < 4; i++) {
			if (i < 2) {
				rooks[i] = new Rook();
			
				rooks[i].xCoordOld = 7 * i;
				rooks[i].yCoordOld = 7;
				rooks[i].colour = true;
				
				master.gameBoard[rooks[i].xCoordOld][rooks[i].yCoordOld] = 4;
				
				WhiteRooks[i] = new ImageView(WhiteRook);
			} else {
				rooks[i] = new Rook();
			
				rooks[i].xCoordOld = 7 * (i - 2);
				rooks[i].yCoordOld = 0;
				rooks[i].colour = false;
				
				master.gameBoard[rooks[i].xCoordOld][rooks[i].yCoordOld] = 10;
				
				WhiteRooks[i] = new ImageView(BlackRook);
			}
			
			
			//Setting the position of the image
			WhiteRooks[i].setX(xOffset + (rooks[i].xCoordOld * squareSize) - (0.1 * squareSize)); 
			WhiteRooks[i].setY(yOffset + (rooks[i].yCoordOld * squareSize) - (0.06 * squareSize)); 
			WhiteRooks[i].setFitHeight(squareSize);
			WhiteRooks[i].setFitWidth(1.185 * squareSize);
			
			pane.getChildren().add(WhiteRooks[i]);
		}
		
		//Knight Generation
		for (int i = 0; i < 4; i++) {
			if (i < 2) {
				knights[i] = new Knight();
			
				knights[i].xCoordOld = 1 + (5 * i);
				knights[i].yCoordOld = 7;
				knights[i].colour = true;
			
				master.gameBoard[knights[i].xCoordOld][knights[i].yCoordOld] = 2;
			
				WhiteKnights[i] = new ImageView(WhiteKnight);
			} else {
				knights[i] = new Knight();
			
				knights[i].xCoordOld = 1 + (5 * (i - 2));
				knights[i].yCoordOld = 0;
				knights[i].colour = false;
			
				master.gameBoard[knights[i].xCoordOld][knights[i].yCoordOld] = 8;
			
				WhiteKnights[i] = new ImageView(BlackKnight);
			}
			
			
			//Setting the position of the image
			WhiteKnights[i].setX(xOffset + (knights[i].xCoordOld * squareSize) - (0.082 * squareSize)); 
			WhiteKnights[i].setY(yOffset + (knights[i].yCoordOld * squareSize) - (0.02 * squareSize)); 
			WhiteKnights[i].setFitHeight(squareSize);
			WhiteKnights[i].setFitWidth(1.185 * squareSize);
			
			pane.getChildren().add(WhiteKnights[i]);
		}
		
		//Bishop Generation
		for (int i = 0; i < 4; i++) {
			if (i < 2) {
				bishops[i] = new Bishop();
			
				bishops[i].xCoordOld = 2 + (3 * i);
				bishops[i].yCoordOld = 7;
				bishops[i].colour = true;
				
				master.gameBoard[bishops[i].xCoordOld][bishops[i].yCoordOld] = 3;
				
				WhiteBishops[i] = new ImageView(WhiteBishop);
			} else {
				bishops[i] = new Bishop();
			
				bishops[i].xCoordOld = 2 + (3 * (i - 2));
				bishops[i].yCoordOld = 0;
				bishops[i].colour = false;
				
				master.gameBoard[bishops[i].xCoordOld][bishops[i].yCoordOld] = 9;
				
				WhiteBishops[i] = new ImageView(BlackBishop);
			}
			
			
			//Setting the position of the image
			WhiteBishops[i].setX(xOffset + (bishops[i].xCoordOld * squareSize) - (0.082 * squareSize)); 
			WhiteBishops[i].setY(yOffset + (bishops[i].yCoordOld * squareSize) - (0.02 * squareSize)); 
			WhiteBishops[i].setFitHeight(squareSize);
			WhiteBishops[i].setFitWidth(1.185 * squareSize);
			
			pane.getChildren().add(WhiteBishops[i]);
		}
		
		//Queen Generation
		for (int i = 0; i < 2; i++) {
			if (i == 0) {
				queens[i] = new Queen();
				
				queens[i].xCoordOld = 3;
				queens[i].yCoordOld = 7;
				queens[i].colour = true;
				
				master.gameBoard[queens[i].xCoordOld][queens[i].yCoordOld] = 5;
				
				WhiteQueens[i] = new ImageView(WhiteQueen);
			} else {
				queens[i] = new Queen();
				
				queens[i].xCoordOld = 3;
				queens[i].yCoordOld = 0;
				queens[i].colour = false;
				
				master.gameBoard[queens[i].xCoordOld][queens[i].yCoordOld] = 11;
				
				WhiteQueens[i] = new ImageView(BlackQueen);
			}
			
			
			//Setting the position of the image
			WhiteQueens[i].setX(xOffset + (queens[i].xCoordOld * squareSize) - (0.092 * squareSize)); 
			WhiteQueens[i].setY(yOffset + (queens[i].yCoordOld * squareSize) - (0.03 * squareSize)); 
			WhiteQueens[i].setFitHeight(squareSize);
			WhiteQueens[i].setFitWidth(1.185 * squareSize);
			
			pane.getChildren().add(WhiteQueens[i]);
		}
		
		//King Generation
		for (int i = 0; i < 2; i++) {
			if (i == 0) {
				kings[i] = new King();
			
				kings[i].xCoordOld = 4;
				kings[i].yCoordOld = 7;
				kings[i].colour = true;
				
				master.gameBoard[kings[i].xCoordOld][kings[i].yCoordOld] = 6;
				
				WhiteKings[i] = new ImageView(WhiteKing);
			} else {
				kings[i] = new King();
			
				kings[i].xCoordOld = 4;
				kings[i].yCoordOld = 0;
				kings[i].colour = false;
				
				master.gameBoard[kings[i].xCoordOld][kings[i].yCoordOld] = 12;
				
				WhiteKings[i] = new ImageView(BlackKing);
			}
			
			
			//Setting the position of the image
			WhiteKings[i].setX(xOffset + (kings[i].xCoordOld * squareSize) - (0.092 * squareSize)); 
			WhiteKings[i].setY(yOffset + (kings[i].yCoordOld * squareSize) - (0.03 * squareSize)); 
			WhiteKings[i].setFitHeight(squareSize);
			WhiteKings[i].setFitWidth(1.185 * squareSize);
			
			pane.getChildren().add(WhiteKings[i]);
		}
		
		printBoard();
		
		/****************************************
		Pawn Movement
		*****************************************/
		
		for (int b = 0; b < 16; b++) {
			
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
					
					if (pawns[i].pawnMove(master.gameBoard)) {
						WhitePawns[i].setX(xOffset + (xAxis * squareSize) - (0.092 * squareSize));
						WhitePawns[i].setY(yOffset + (yAxis * squareSize) - (0.06 * squareSize));
						
						int x = (int)xAxis;
						int y = (int)yAxis;
						
						board[pawns[i].xCoordOld][pawns[i].yCoordOld] = 0;
						board[pawns[i].xCoordNew][pawns[i].yCoordNew] = 1;
						
						master.gameBoard[pawns[i].xCoordOld][pawns[i].yCoordOld] = 0;
						
						if (pawns[i].colour) {
							master.gameBoard[pawns[i].xCoordNew][pawns[i].yCoordNew] = 1;
						} else {
							master.gameBoard[pawns[i].xCoordNew][pawns[i].yCoordNew] = 7;
						}
						
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
		
		/****************************************
		Rooks, Knights, Bishops Movement
		*****************************************/
		
		for (int b = 0; b < 4; b++) {
			int i = b;
			
			//Rooks*******************************************
			
			WhiteRooks[i].setOnMousePressed(new EventHandler <MouseEvent>()
			{
				public void handle(MouseEvent event)
				{
					event.setDragDetect(true);
				}
			});
			
			WhiteRooks[i].setOnMouseReleased(new EventHandler <MouseEvent>()
			{
				public void handle(MouseEvent event)
				{
					
					double xAxis = Math.floor((WhiteRooks[i].getX() + (squareSize/2) - xOffset)/squareSize);
					double yAxis = Math.floor((WhiteRooks[i].getY() + (squareSize/2) - yOffset)/squareSize);
					
					int xint = (int)xAxis;
					int yint = (int)yAxis;
					
					rooks[i].xCoordNew = xint;
					rooks[i].yCoordNew = yint;
					
					
					if (rooks[i].rookMove(master.gameBoard)) {
						WhiteRooks[i].setX(xOffset + (xAxis * squareSize) - (0.092 * squareSize));
						WhiteRooks[i].setY(yOffset + (yAxis * squareSize) - (0.06 * squareSize));
						
						int x = (int)xAxis;
						int y = (int)yAxis;
						
						board[rooks[i].xCoordOld][rooks[i].yCoordOld] = 0;
						board[rooks[i].xCoordNew][rooks[i].yCoordNew] = 4;
						
						master.gameBoard[rooks[i].xCoordOld][rooks[i].yCoordOld] = 0;
						
						if (rooks[i].colour) {
							master.gameBoard[rooks[i].xCoordNew][rooks[i].yCoordNew] = 4;
						} else {
							master.gameBoard[rooks[i].xCoordNew][rooks[i].yCoordNew] = 10;
						}
						
						rooks[i].xCoordOld = x;
						rooks[i].yCoordOld = y;
						
						printBoard();
					} else {
						WhiteRooks[i].setX(xOffset - (0.092 * squareSize) + (rooks[i].xCoordOld * squareSize));
						WhiteRooks[i].setY(yOffset - (0.06 * squareSize) + (rooks[i].yCoordOld * squareSize));
					}
				}
			});
			
			WhiteRooks[i].setOnMouseDragged(new EventHandler <MouseEvent>()
			{
				public void handle(MouseEvent event)
				{
					double mouseX = event.getX();
					double mouseY = event.getY();
					
					WhiteRooks[i].setX(mouseX - (squareSize/2));
					WhiteRooks[i].setY(mouseY - (squareSize/2));
					
					event.setDragDetect(false);
				}
			});
			
			//Knights*******************************************
			
			WhiteKnights[i].setOnMousePressed(new EventHandler <MouseEvent>()
			{
				public void handle(MouseEvent event)
				{
					event.setDragDetect(true);
				}
			});
			
			WhiteKnights[i].setOnMouseReleased(new EventHandler <MouseEvent>()
			{
				public void handle(MouseEvent event)
				{
					
					double xAxis = Math.floor((WhiteKnights[i].getX() + (squareSize/2) - xOffset)/squareSize);
					double yAxis = Math.floor((WhiteKnights[i].getY() + (squareSize/2) - yOffset)/squareSize);
					
					int xint = (int)xAxis;
					int yint = (int)yAxis;
					
					knights[i].xCoordNew = xint;
					knights[i].yCoordNew = yint;
					
					if (knights[i].knightMove(master.gameBoard)) {
						WhiteKnights[i].setX(xOffset + (xAxis * squareSize) - (0.092 * squareSize));
						WhiteKnights[i].setY(yOffset + (yAxis * squareSize) - (0.06 * squareSize));
						
						int x = (int)xAxis;
						int y = (int)yAxis;
						
						board[knights[i].xCoordOld][knights[i].yCoordOld] = 0;
						board[knights[i].xCoordNew][knights[i].yCoordNew] = 2;
						
						master.gameBoard[knights[i].xCoordOld][knights[i].yCoordOld] = 0;
						
						if (knights[i].colour) {
							master.gameBoard[knights[i].xCoordNew][knights[i].yCoordNew] = 2;
						} else {
							master.gameBoard[knights[i].xCoordNew][knights[i].yCoordNew] = 8;
						}
						
						knights[i].xCoordOld = x;
						knights[i].yCoordOld = y;
					} else {
						WhiteKnights[i].setX(xOffset - (0.092 * squareSize) + (knights[i].xCoordOld * squareSize));
						WhiteKnights[i].setY(yOffset - (0.06 * squareSize) + (knights[i].yCoordOld * squareSize));
					}
				}
			});
			
			WhiteKnights[i].setOnMouseDragged(new EventHandler <MouseEvent>()
			{
				public void handle(MouseEvent event)
				{
					double mouseX = event.getX();
					double mouseY = event.getY();
					
					WhiteKnights[i].setX(mouseX - (squareSize/2));
					WhiteKnights[i].setY(mouseY - (squareSize/2));
					
					event.setDragDetect(false);
				}
			});
			
			//Bishops*******************************************
			
			WhiteBishops[i].setOnMousePressed(new EventHandler <MouseEvent>()
			{
				public void handle(MouseEvent event)
				{
					event.setDragDetect(true);
				}
			});
			
			WhiteBishops[i].setOnMouseReleased(new EventHandler <MouseEvent>()
			{
				public void handle(MouseEvent event)
				{
					
					double xAxis = Math.floor((WhiteBishops[i].getX() + (squareSize/2) - xOffset)/squareSize);
					double yAxis = Math.floor((WhiteBishops[i].getY() + (squareSize/2) - yOffset)/squareSize);
					
					int xint = (int)xAxis;
					int yint = (int)yAxis;
					
					bishops[i].xCoordNew = xint;
					bishops[i].yCoordNew = yint;
					
					if (bishops[i].bishopMove(master.gameBoard)) {
						WhiteBishops[i].setX(xOffset + (xAxis * squareSize) - (0.092 * squareSize));
						WhiteBishops[i].setY(yOffset + (yAxis * squareSize) - (0.06 * squareSize));
						
						int x = (int)xAxis;
						int y = (int)yAxis;
						
						board[bishops[i].xCoordOld][bishops[i].yCoordOld] = 0;
						board[bishops[i].xCoordNew][bishops[i].yCoordNew] = 3;
						
						master.gameBoard[bishops[i].xCoordOld][bishops[i].yCoordOld] = 0;
						
						if (bishops[i].colour) {
							master.gameBoard[bishops[i].xCoordNew][bishops[i].yCoordNew] = 3;
						} else {
							master.gameBoard[bishops[i].xCoordNew][bishops[i].yCoordNew] = 9;
						}
						
						bishops[i].xCoordOld = x;
						bishops[i].yCoordOld = y;
					} else {
						WhiteBishops[i].setX(xOffset - (0.092 * squareSize) + (bishops[i].xCoordOld * squareSize));
						WhiteBishops[i].setY(yOffset - (0.06 * squareSize) + (bishops[i].yCoordOld * squareSize));
					}
				}
			});
			
			WhiteBishops[i].setOnMouseDragged(new EventHandler <MouseEvent>()
			{
				public void handle(MouseEvent event)
				{
					double mouseX = event.getX();
					double mouseY = event.getY();
					
					WhiteBishops[i].setX(mouseX - (squareSize/2));
					WhiteBishops[i].setY(mouseY - (squareSize/2));
					
					event.setDragDetect(false);
				}
			});
		}
		
		/****************************************
		Queens, Kings Movement
		*****************************************/
		
		for (int b = 0; b < 2; b++) {
			
			int i = b;
			
			WhiteQueens[i].setOnMousePressed(new EventHandler <MouseEvent>()
			{
				public void handle(MouseEvent event)
				{
					event.setDragDetect(true);
				}
			});
			
			WhiteQueens[i].setOnMouseReleased(new EventHandler <MouseEvent>()
			{
				public void handle(MouseEvent event)
				{
					
					double xAxis = Math.floor((WhiteQueens[i].getX() + (squareSize/2) - xOffset)/squareSize);
					double yAxis = Math.floor((WhiteQueens[i].getY() + (squareSize/2) - yOffset)/squareSize);
					
					int xint = (int)xAxis;
					int yint = (int)yAxis;
					
					queens[i].xCoordNew = xint;
					queens[i].yCoordNew = yint;
					
					if (queens[i].queenMove(master.gameBoard)) {
						WhiteQueens[i].setX(xOffset + (xAxis * squareSize) - (0.092 * squareSize));
						WhiteQueens[i].setY(yOffset + (yAxis * squareSize) - (0.06 * squareSize));
						
						int x = (int)xAxis;
						int y = (int)yAxis;
						
						board[queens[i].xCoordOld][queens[i].yCoordOld] = 0;
						board[queens[i].xCoordNew][queens[i].yCoordNew] = 5;
						
						master.gameBoard[queens[i].xCoordOld][queens[i].yCoordOld] = 0;
						
						if (queens[i].colour) {
							master.gameBoard[queens[i].xCoordNew][queens[i].yCoordNew] = 5;
						} else {
							master.gameBoard[queens[i].xCoordNew][queens[i].yCoordNew] = 11;
						}
						
						queens[i].xCoordOld = x;
						queens[i].yCoordOld = y;
						
						printBoard();
					} else {
						WhiteQueens[i].setX(xOffset - (0.092 * squareSize) + (queens[i].xCoordOld * squareSize));
						WhiteQueens[i].setY(yOffset - (0.06 * squareSize) + (queens[i].yCoordOld * squareSize));
					}
				}
			});
			
			WhiteQueens[i].setOnMouseDragged(new EventHandler <MouseEvent>()
			{
				public void handle(MouseEvent event)
				{
					double mouseX = event.getX();
					double mouseY = event.getY();
					
					WhiteQueens[i].setX(mouseX - (squareSize/2));
					WhiteQueens[i].setY(mouseY - (squareSize/2));
					
					event.setDragDetect(false);
				}
			});
			
			WhiteKings[i].setOnMousePressed(new EventHandler <MouseEvent>()
			{
				public void handle(MouseEvent event)
				{
					event.setDragDetect(true);
				}
			});
			
			WhiteKings[i].setOnMouseReleased(new EventHandler <MouseEvent>()
			{
				public void handle(MouseEvent event)
				{
					
					double xAxis = Math.floor((WhiteKings[i].getX() + (squareSize/2) - xOffset)/squareSize);
					double yAxis = Math.floor((WhiteKings[i].getY() + (squareSize/2) - yOffset)/squareSize);
					
					int xint = (int)xAxis;
					int yint = (int)yAxis;
					
					kings[i].xCoordNew = xint;
					kings[i].yCoordNew = yint;
					
					if (kings[i].kingMove(master.gameBoard)) {
						WhiteKings[i].setX(xOffset + (xAxis * squareSize) - (0.092 * squareSize));
						WhiteKings[i].setY(yOffset + (yAxis * squareSize) - (0.06 * squareSize));
						
						int x = (int)xAxis;
						int y = (int)yAxis;
						
						board[kings[i].xCoordOld][kings[i].yCoordOld] = 0;
						board[kings[i].xCoordNew][kings[i].yCoordNew] = 6;
						
						master.gameBoard[kings[i].xCoordOld][kings[i].yCoordOld] = 0;
						
						if (kings[i].colour) {
							master.gameBoard[kings[i].xCoordNew][kings[i].yCoordNew] = 6;
						} else {
							master.gameBoard[kings[i].xCoordNew][kings[i].yCoordNew] = 12;
						}
						
						kings[i].xCoordOld = x;
						kings[i].yCoordOld = y;
						
						printBoard();
					} else {
						WhiteKings[i].setX(xOffset - (0.092 * squareSize) + (kings[i].xCoordOld * squareSize));
						WhiteKings[i].setY(yOffset - (0.06 * squareSize) + (kings[i].yCoordOld * squareSize));
					}
				}
			});
			
			WhiteKings[i].setOnMouseDragged(new EventHandler <MouseEvent>()
			{
				public void handle(MouseEvent event)
				{
					double mouseX = event.getX();
					double mouseY = event.getY();
					
					WhiteKings[i].setX(mouseX - (squareSize/2));
					WhiteKings[i].setY(mouseY - (squareSize/2));
					
					event.setDragDetect(false);
				}
			});
		}
		
		myStage.show();
	}
}