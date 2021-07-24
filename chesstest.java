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

class Game {
	double xOffset;
	double yOffset;
	double imageXOffset;
	double imageYOffset;
	double squareSize;
	boolean turn;
	boolean[][] possibilityBoard;
	Rectangle[][] chessBoard;
	ImageView[][] pieceGraphicsMaster;
	logicContainer pieceLogic;
	Piece[][] arrayLogic;
	Pane pane;
	
	public Game (double xOffset, double yOffset, double squareSize, Pane pane, boolean turn) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.squareSize = squareSize;
		this.pane = pane;
		this.turn = turn;
	}
	
	public void initBoard() {
		this.chessBoard = new Rectangle[8][8];
		
		boolean squareColour = true;
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				chessBoard[i][j] = new Rectangle((j * this.squareSize) + this.xOffset, (i * this.squareSize) + this.yOffset, this.squareSize, this.squareSize);
				
				if (squareColour) {
					chessBoard[i][j].setFill(Color.rgb(221, 227, 237));
				} else {
					chessBoard[i][j].setFill(Color.rgb(145, 164, 194));
				}
				
				squareColour =! squareColour;
				
			}
			squareColour =! squareColour;
			
		}
	}
	
	public void piecePos(ImageView piece, int x, int y, double xOff, double yOff) {
		piece.setX(xOffset + (x * squareSize) - (xOff * squareSize)); 
		piece.setY(yOffset + (y * squareSize) - (yOff * squareSize)); 
		piece.setFitHeight(squareSize);
		piece.setFitWidth(1.185 * squareSize);
	}
	
	public void initPieces(int board[][]) throws FileNotFoundException {
		//Assigning images
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
				
		this.pieceGraphicsMaster = new ImageView[8][8];
		this.pieceLogic = new logicContainer();
		this.arrayLogic = new Piece[8][8];
		this.possibilityBoard = new boolean[8][8];
		
		int pawnTrack = 0;
		int knightTrack = 0;
		int bishopTrack = 0;
		int rookTrack = 0;
		int queenTrack = 0;
		int kingTrack = 0;
		int imageTrack = 0;
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				//int x = j;
				//int y = i;
				
				switch (board[j][i]) {
					case 1:
						this.pieceGraphicsMaster[j][i] = new ImageView(WhitePawn);
						piecePos(this.pieceGraphicsMaster[j][i], j, i, 0.092, 0.07);
						
						this.pieceLogic.pawns[pawnTrack] = new Pawn(j, i, true, true, false);
						pawnTrack++;
						imageTrack++;
						
						this.arrayLogic[j][i] = new Pawn(j, i, true, true, false);
						
						break;               
					case 2:
						this.pieceGraphicsMaster[j][i] = new ImageView(WhiteKnight);
						piecePos(this.pieceGraphicsMaster[j][i], j, i, 0.082, 0.02);
						
						this.pieceLogic.knights[knightTrack] = new Knight(j, i, true, false);
						knightTrack++;
						imageTrack++;
						
						this.arrayLogic[j][i] = new Knight(j, i, true, false);
						
						break;               
					case 3:                  
						this.pieceGraphicsMaster[j][i] = new ImageView(WhiteBishop);
						piecePos(this.pieceGraphicsMaster[j][i], j, i, 0.082, 0.02);
						
						this.pieceLogic.bishops[bishopTrack] = new Bishop(j, i, true, false);
						bishopTrack++;
						imageTrack++;
						
						this.arrayLogic[j][i] = new Bishop(j, i, true, false);
						
						break;               
					case 4:                  
						this.pieceGraphicsMaster[j][i] = new ImageView(WhiteRook);
						piecePos(this.pieceGraphicsMaster[j][i], j, i, 0.1, 0.06);
						
						this.pieceLogic.rooks[rookTrack] = new Rook(j, i, true, false);
						rookTrack++;
						imageTrack++;
						
						this.arrayLogic[j][i] = new Rook(j, i, true, false);
						
						break;               
					case 5:                  
						this.pieceGraphicsMaster[j][i] = new ImageView(WhiteQueen);
						piecePos(this.pieceGraphicsMaster[j][i], j, i, 0.092, 0.03);
						
						this.pieceLogic.queens[queenTrack] = new Queen(j, i, true, false);
						queenTrack++;
						imageTrack++;
						
						this.arrayLogic[j][i] = new Queen(j, i, true, false);
						
						break;               
					case 6:                  
						this.pieceGraphicsMaster[j][i] = new ImageView(WhiteKing);
						piecePos(this.pieceGraphicsMaster[j][i], j, i, 0.092, 0.03);
						
						this.pieceLogic.kings[kingTrack] = new King(j, i, true, false);
						kingTrack++;
						imageTrack++;
						
						this.arrayLogic[j][i] = new King(j, i, true, false);
						
						break;               
					case 7:                  
						this.pieceGraphicsMaster[j][i] = new ImageView(BlackPawn);
						piecePos(this.pieceGraphicsMaster[j][i], j, i, 0.092, 0.07);

						this.pieceLogic.pawns[pawnTrack] = new Pawn(j, i, true, false, false);
						pawnTrack++;
						imageTrack++;
						
						this.arrayLogic[j][i] = new Pawn(j, i, true, false, false);
						
						break;               
					case 8:                  
						this.pieceGraphicsMaster[j][i] = new ImageView(BlackKnight);
						piecePos(this.pieceGraphicsMaster[j][i], j, i, 0.082, 0.02);
						
						this.pieceLogic.knights[knightTrack] = new Knight(j, i, false, false);
						knightTrack++;
						imageTrack++;
						
						this.arrayLogic[j][i] = new Knight(j, i, false, false);
						
						break;               
					case 9:                  
						this.pieceGraphicsMaster[j][i] = new ImageView(BlackBishop);
						piecePos(this.pieceGraphicsMaster[j][i], j, i, 0.082, 0.02);
						
						this.pieceLogic.bishops[bishopTrack] = new Bishop(j, i, false, false);
						bishopTrack++;
						imageTrack++;
						
						this.arrayLogic[j][i] = new Bishop(j, i, false, false);
						
						break;
					case 10:              
						this.pieceGraphicsMaster[j][i] = new ImageView(BlackRook);
						piecePos(this.pieceGraphicsMaster[j][i], j, i, 0.1, 0.06);
						
						this.pieceLogic.rooks[rookTrack] = new Rook(j, i, false, false);
						rookTrack++;
						imageTrack++;
						
						this.arrayLogic[j][i] = new Rook(j, i, false, false);
						
						break;               
					case 11:                 
						this.pieceGraphicsMaster[j][i] = new ImageView(BlackQueen);
						piecePos(this.pieceGraphicsMaster[j][i], j, i, 0.092, 0.03);
						
						this.pieceLogic.queens[queenTrack] = new Queen(j, i, false, false);
						queenTrack++;
						imageTrack++;
						
						this.arrayLogic[j][i] = new Queen(j, i, false, false);
						
						break;               
					case 12:                 
						this.pieceGraphicsMaster[j][i] = new ImageView(BlackKing);
						piecePos(this.pieceGraphicsMaster[j][i], j, i, 0.092, 0.03);
						
						this.pieceLogic.kings[kingTrack] = new King(j, i, false, false);
						kingTrack++;
						imageTrack++;
						
						this.arrayLogic[j][i] = new King(j, i, false, false);
						
						break;
					default:
						this.pieceGraphicsMaster[j][i] = new ImageView();
						
						break;
				}
			}
		}
	}
	
	public void movePiece(boolean moveCheck, ImageView graphic, Piece pieceSelect, int[][] board, double xAxis, double yAxis, int newSquare, double xOff, double yOff) {
		Piece logic = pieceSelect;
		
		if (moveCheck) {
			graphic.setX(this.xOffset + (xAxis * squareSize) - (xOff * squareSize));
			graphic.setY(this.yOffset + (yAxis * squareSize) - (yOff * squareSize));
			
			int x = (int)xAxis;
			int y = (int)yAxis;
			
			if (board[logic.xCoordNew][logic.yCoordNew] > 0) {
				this.pane.getChildren().remove(this.pieceGraphicsMaster[logic.xCoordNew][logic.yCoordNew]);
			}
			
			if (board[logic.xCoordNew][logic.yCoordNew] == 1 || board[logic.xCoordNew][logic.yCoordNew] == 7) {
				Pawn testPawn = new Pawn(logic.xCoordNew, logic.yCoordNew, logic.firstMove, logic.colour, false);

				if (testPawn.isEndRank()) {
					
				}
			}
			
			this.pieceGraphicsMaster[logic.xCoordNew][logic.yCoordNew] = this.pieceGraphicsMaster[logic.xCoordOld][logic.yCoordOld];
			this.pieceGraphicsMaster[logic.xCoordOld][logic.yCoordOld] = new ImageView();
			
			this.pane.getChildren().remove(this.pieceGraphicsMaster[logic.xCoordOld][logic.yCoordOld]);
			
			this.arrayLogic[logic.xCoordNew][logic.yCoordNew] = this.arrayLogic[logic.xCoordOld][logic.yCoordOld];
			this.arrayLogic[logic.xCoordOld][logic.yCoordOld] = new Piece();
			
			board[logic.xCoordOld][logic.yCoordOld] = 0;
			board[logic.xCoordNew][logic.yCoordNew] = newSquare;
			
			this.arrayLogic[logic.xCoordNew][logic.yCoordNew].xCoordOld = this.arrayLogic[logic.xCoordNew][logic.yCoordNew].xCoordNew;
			this.arrayLogic[logic.xCoordNew][logic.yCoordNew].yCoordOld = this.arrayLogic[logic.xCoordNew][logic.yCoordNew].yCoordNew;
			
			this.turn = !this.turn;
		} else {
			graphic.setX(this.xOffset - (xOff * this.squareSize) + (logic.xCoordOld * this.squareSize));
			graphic.setY(this.yOffset - (yOff * this.squareSize) + (logic.yCoordOld * this.squareSize));
		}
	}
	
}

public class chesstest extends Application {
	public static void main (String[] args) {
		
		launch(args);
	}
	
	public void start(Stage myStage) throws FileNotFoundException {
		Piece master = new Piece();
		master.gameBoard = new int[8][8];
		
		Board logicBoard = new Board();
		logicBoard.initState();
		
		double xOffset = 25;
		double yOffset = 25;
		double squareSize = 100;
		Pane pane = new Pane();
		
		Game Game = new Game(xOffset, yOffset, squareSize, pane, true);
		Game.initBoard();
		Game.initPieces(logicBoard.logicBoard);
		
		myStage.setResizable(true);

		myStage.setTitle("Chess");

		Scene myScene = new Scene(Game.pane, 850, 850);

		myStage.setScene(myScene);
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Game.pane.getChildren().add(Game.chessBoard[j][i]);
			}
		}
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Game.pane.getChildren().add(Game.pieceGraphicsMaster[j][i]);
			}
		}
		
		selectPiece selector = new selectPiece(false);
		
		myScene.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {				
				selector.mouseX = event.getX();
				selector.mouseY = event.getY();
				
				double targetX = Math.floor((selector.mouseX - xOffset)/squareSize);
				double targetY = Math.floor((selector.mouseY - yOffset)/squareSize);
				
				selector.xSelect = (int)targetX;
				selector.ySelect = (int)targetY;

				int x = selector.xSelect;
				int y = selector.ySelect;
				
				System.out.println("This Piece can move to the following squares:");

				switch (logicBoard.logicBoard[x][y]) {
					case 1:
					case 7:
						Pawn myPawn = new Pawn(x, y, Game.arrayLogic[x][y].firstMove, Game.arrayLogic[x][y].colour, false);
						Game.imageXOffset = 0.092;
						Game.imageYOffset = 0.07;
						
						for (int i = 0; i < 8; i++) {
							for (int j = 0; j < 8; j++) {
								
								myPawn.xCoordNew = j;
								myPawn.yCoordNew = i;
								
								if (myPawn.Move(logicBoard.logicBoard, Game.turn)) {
									Game.possibilityBoard[j][i] = true;
									System.out.println("X: " + j + " Y: " + i);
								} else {
									Game.possibilityBoard[j][i] = false;
								}
							}
						}
						
						break;
					case 2:
					case 8:
						Knight myKnight = new Knight(x, y, Game.arrayLogic[x][y].colour, false);
						Game.imageXOffset = 0.082;
						Game.imageYOffset = 0.02;

						for (int i = 0; i < 8; i++) {
							for (int j = 0; j < 8; j++) {
								
								myKnight.xCoordNew = j;
								myKnight.yCoordNew = i;
								
								if (myKnight.Move(logicBoard.logicBoard, Game.turn)) {
									Game.possibilityBoard[j][i] = true;
									System.out.println("X: " + j + " Y: " + i);
								} else {
									Game.possibilityBoard[j][i] = false;
								}
							}
						}
						
						break;
					case 3:
					case 9:
						Bishop myBishop = new Bishop(x, y, Game.arrayLogic[x][y].colour, false);
						Game.imageXOffset = 0.082;
						Game.imageYOffset = 0.02;

						for (int i = 0; i < 8; i++) {
							for (int j = 0; j < 8; j++) {
								
								myBishop.xCoordNew = j;
								myBishop.yCoordNew = i;
								
								if (myBishop.Move(logicBoard.logicBoard, Game.turn)) {
									Game.possibilityBoard[j][i] = true;
									System.out.println("X: " + j + " Y: " + i);
								} else {
									Game.possibilityBoard[j][i] = false;
								}
							}
						}
						
						break;
					case 4:
					case 10:
						Rook myRook = new Rook(x, y, Game.arrayLogic[x][y].colour, false);
						Game.imageXOffset = 0.1;
						Game.imageYOffset = 0.06;
						
						for (int i = 0; i < 8; i++) {
							for (int j = 0; j < 8; j++) {
								
								myRook.xCoordNew = j;
								myRook.yCoordNew = i;
								
								if (myRook.Move(logicBoard.logicBoard, Game.turn)) {
									Game.possibilityBoard[j][i] = true;
									System.out.println("X: " + j + " Y: " + i);
								} else {
									Game.possibilityBoard[j][i] = false;
								}
							}
						}
						
						break;
					case 5:
					case 11:
						Queen myQueen = new Queen(x, y, Game.arrayLogic[x][y].colour, false);
						Game.imageXOffset = 0.092;
						Game.imageYOffset = 0.03;
						
						for (int i = 0; i < 8; i++) {
							for (int j = 0; j < 8; j++) {
								
								myQueen.xCoordNew = j;
								myQueen.yCoordNew = i;
								
								if (myQueen.Move(logicBoard.logicBoard, Game.turn)) {
									Game.possibilityBoard[j][i] = true;
									System.out.println("X: " + j + " Y: " + i);
								} else {
									Game.possibilityBoard[j][i] = false;
								}
							}
						}						
						
						break;
					case 6:
					case 12:
						King myKing = new King(x, y, Game.arrayLogic[x][y].colour, false);
						Game.imageXOffset = 0.092;
						Game.imageYOffset = 0.03;
						
						for (int i = 0; i < 8; i++) {
							for (int j = 0; j < 8; j++) {
								
								myKing.xCoordNew = j;
								myKing.yCoordNew = i;
								
								if (myKing.Move(logicBoard.logicBoard, Game.turn)) {
									Game.possibilityBoard[j][i] = true;
									System.out.println("X: " + j + " Y: " + i);
								} else {
									Game.possibilityBoard[j][i] = false;
								}
							}
						}						
						
						break;
					default:
						break;
				}
				
				
			}
		});
		
		myScene.setOnMouseReleased(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				
				int x = selector.xSelect;
				int y = selector.ySelect;
				
				if (x > -1 && x < 8 && y > -1 && y < 8) {
					if (logicBoard.logicBoard[x][y] > 0) {
						double xAxis = Math.floor((Game.pieceGraphicsMaster[x][y].getX() + (squareSize/2) - xOffset)/squareSize);
						double yAxis = Math.floor((Game.pieceGraphicsMaster[x][y].getY() + (squareSize/2) - yOffset)/squareSize);
						
						int xint = (int)xAxis;
						int yint = (int)yAxis;
						
						Game.arrayLogic[x][y].xCoordNew = xint;
						Game.arrayLogic[x][y].yCoordNew = yint;
						
						boolean canMove = true;
						
						if (Game.possibilityBoard[xint][yint] == false) {
							canMove = false;
						}
						
						Game.movePiece(canMove, Game.pieceGraphicsMaster[x][y], Game.arrayLogic[x][y], logicBoard.logicBoard, xint, yint, logicBoard.logicBoard[x][y], Game.imageXOffset, Game.imageYOffset);
					}
				}
			}
		});
		
		myScene.setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {		
				selector.mouseX = event.getX();
				selector.mouseY = event.getY();
				
				if (logicBoard.logicBoard[selector.xSelect][selector.ySelect] > 0) {
					if (Game.turn == Game.arrayLogic[selector.xSelect][selector.ySelect].colour) {
						Game.pieceGraphicsMaster[selector.xSelect][selector.ySelect].setX(selector.mouseX - (squareSize/2));
						Game.pieceGraphicsMaster[selector.xSelect][selector.ySelect].setY(selector.mouseY - (squareSize/2));
					}
				}
				
			}
		});

		
		/*
		for (int h = 0; h < 8; h++) {
			for (int g = 0; g < 8; g++) {
				int i = h;
				int j = g;
			
				Game.pieceGraphicsMaster[j][i].setOnMousePressed(new EventHandler <MouseEvent>()
				{
					public void handle(MouseEvent event)
					{
						double oldXdouble = Math.floor((Game.pieceGraphicsMaster[j][i].getX() + (squareSize/2) - xOffset)/squareSize);
						double oldYdouble = Math.floor((Game.pieceGraphicsMaster[j][i].getY() + (squareSize/2) - yOffset)/squareSize);
						savestate.x = (int)oldXdouble;
						savestate.y = (int)oldYdouble;
						
						event.setDragDetect(true);
					}
				});
			
				Game.pieceGraphicsMaster[j][i].setOnMouseReleased(new EventHandler <MouseEvent>()
				{
					public void handle(MouseEvent event)
					{
						
						double xAxis = Math.floor((Game.pieceGraphicsMaster[j][i].getX() + (squareSize/2) - xOffset)/squareSize);
						double yAxis = Math.floor((Game.pieceGraphicsMaster[j][i].getY() + (squareSize/2) - yOffset)/squareSize);
						
						int xint = (int)xAxis;
						int yint = (int)yAxis;
						
						boolean canMove = true;
						int selectedPiece = 0;
						
						double imageXOffset = 0;
						double imageYOffset = 0;
						
						Game.arrayLogic[j][i].xCoordNew = xint;
						Game.arrayLogic[j][i].yCoordNew = yint;
						
						//System.out.println("selectedPiece xOld: " + Game.arrayLogic[j][i].xCoordOld);
						//System.out.println("selectedPiece yOld: " + Game.arrayLogic[j][i].yCoordOld);
						//System.out.println("selectedPiece xNew: " + Game.arrayLogic[j][i].xCoordNew);
						//System.out.println("selectedPiece yNew: " + Game.arrayLogic[j][i].yCoordNew);
						
						switch (logicBoard.logicBoard[j][i]) {
							case 1:
							case 7:
								Pawn myPawn = new Pawn(j, i, Game.arrayLogic[j][i].firstMove, Game.arrayLogic[j][i].colour, false);
								myPawn.xCoordNew = Game.arrayLogic[j][i].xCoordNew;
								myPawn.yCoordNew = Game.arrayLogic[j][i].yCoordNew;
								imageXOffset = 0.092;
								imageYOffset = 0.07;
							
								//System.out.println("Pawn xOld: " + myPawn.xCoordOld);
								//System.out.println("Pawn yOld: " + myPawn.yCoordOld);
								//System.out.println("Pawn xNew: " + myPawn.xCoordNew);
								//System.out.println("Pawn yNew: " + myPawn.yCoordNew);
								//System.out.println("Colour: " + Game.arrayLogic[j][i].colour);
							
								canMove = myPawn.pawnMove(logicBoard.logicBoard);
								break;
							case 2:
							case 8:
								Knight myKnight = new Knight(j, i, Game.arrayLogic[j][i].colour, false);
								myKnight.xCoordNew = Game.arrayLogic[j][i].xCoordNew;
								myKnight.yCoordNew = Game.arrayLogic[j][i].yCoordNew;
								imageXOffset = 0.082;
								imageYOffset = 0.02;
							
								canMove = myKnight.knightMove(logicBoard.logicBoard);
								break;
							case 3:
							case 9:
								Bishop myBishop = new Bishop(j, i, Game.arrayLogic[j][i].colour, false);
								myBishop.xCoordNew = Game.arrayLogic[j][i].xCoordNew;
								myBishop.yCoordNew = Game.arrayLogic[j][i].yCoordNew;
								imageXOffset = 0.1;
								imageYOffset = 0.06;
							
								canMove = myBishop.bishopMove(logicBoard.logicBoard);
								break;
							case 4:
							case 10:
								Rook myRook = new Rook(j, i, Game.arrayLogic[j][i].colour, false);
								myRook.xCoordNew = Game.arrayLogic[j][i].xCoordNew;
								myRook.yCoordNew = Game.arrayLogic[j][i].yCoordNew;
								imageXOffset = 0.092;
								imageYOffset = 0.03;
							
								canMove = myRook.rookMove(logicBoard.logicBoard);
								break;
							case 5:
							case 11:
								Queen myQueen = new Queen(j, i, Game.arrayLogic[j][i].colour, false);
								myQueen.xCoordNew = Game.arrayLogic[j][i].xCoordNew;
								myQueen.yCoordNew = Game.arrayLogic[j][i].yCoordNew;
								imageXOffset = 0.092;
								imageYOffset = 0.03;
							
								canMove = myQueen.queenMove(logicBoard.logicBoard);
								break;
							case 6:
							case 12:
								King myKing = new King(j, i, Game.arrayLogic[j][i].colour, false);
								myKing.xCoordNew = Game.arrayLogic[j][i].xCoordNew;
								myKing.yCoordNew = Game.arrayLogic[j][i].yCoordNew;
								imageXOffset = 0.092;
								imageYOffset = 0.06;
								
								canMove = myKing.kingMove(logicBoard.logicBoard);
								break;
							default:
								break;
						}
						
						Game.movePiece(canMove, Game.pieceGraphicsMaster[j][i], Game.arrayLogic[j][i], logicBoard.logicBoard, xAxis, yAxis, logicBoard.logicBoard[j][i], imageXOffset, imageYOffset);
					}
				});
				
				Game.pieceGraphicsMaster[j][i].setOnMouseDragged(new EventHandler <MouseEvent>()
				{
					public void handle(MouseEvent event)
					{
						double mouseX = event.getX();
						double mouseY = event.getY();
						
						Game.pieceGraphicsMaster[j][i].setX(mouseX - (squareSize/2));
						Game.pieceGraphicsMaster[j][i].setY(mouseY - (squareSize/2));
						
						System.out.println("x: " + j + " y: " + i);
						
						for (int z = 0; z < 8; z++) {
							//Game.pieceGraphicsMaster[4][4].setX(mouseX - (squareSize/2));
							//Game.pieceGraphicsMaster[4][4].setY(mouseY - (squareSize/2));
						}
						
						//event.setDragDetect(false);
					}
				});
			}
		}*/

		/****************************************************************************
		PIECE REPRESENTATION AND GENERATION
		****************************************************************************/
		
		/*
		//Pawn Generation
		for (int i = 0; i < 16; i++) {
			if (i < 8) {
				pawns[i] = new Pawn();
			
				pawns[i].xCoordOld = i;
				pawns[i].yCoordOld = 6;
				pawns[i].firstMove = true;
				pawns[i].colour = true;
				
				master.gameBoard[i][6] = 1;
				
				PawnGraphics[i] = new ImageView(WhitePawn);
			} else {
				pawns[i] = new Pawn();
			
				pawns[i].xCoordOld = i - 8;
				pawns[i].yCoordOld = 1;
				pawns[i].firstMove = true;
				pawns[i].colour = false;
			
				master.gameBoard[pawns[i].xCoordOld][pawns[i].yCoordOld] = 7;
			
				PawnGraphics[i] = new ImageView(BlackPawn);
			}
			
			
			//Setting the position of the image 
			PawnGraphics[i].setX(xOffset + (pawns[i].xCoordOld * squareSize) - (0.092 * squareSize)); 
			PawnGraphics[i].setY(yOffset + (pawns[i].yCoordOld * squareSize) - (0.07 * squareSize)); 
			PawnGraphics[i].setFitHeight(squareSize);
			PawnGraphics[i].setFitWidth(1.185 * squareSize);
			
			pane.getChildren().add(PawnGraphics[i]);
		}
		
		//Rook Generation
		for (int i = 0; i < 4; i++) {
			if (i < 2) {
				rooks[i] = new Rook();
			
				rooks[i].xCoordOld = 7 * i;
				rooks[i].yCoordOld = 7;
				rooks[i].colour = true;
				
				master.gameBoard[rooks[i].xCoordOld][rooks[i].yCoordOld] = 4;
				
				RookGraphics[i] = new ImageView(WhiteRook);
			} else {
				rooks[i] = new Rook();
			
				rooks[i].xCoordOld = 7 * (i - 2);
				rooks[i].yCoordOld = 0;
				rooks[i].colour = false;
				
				master.gameBoard[rooks[i].xCoordOld][rooks[i].yCoordOld] = 10;
				
				RookGraphics[i] = new ImageView(BlackRook);
			}
			
			
			//Setting the position of the image
			RookGraphics[i].setX(xOffset + (rooks[i].xCoordOld * squareSize) - (0.1 * squareSize)); 
			RookGraphics[i].setY(yOffset + (rooks[i].yCoordOld * squareSize) - (0.06 * squareSize)); 
			RookGraphics[i].setFitHeight(squareSize);
			RookGraphics[i].setFitWidth(1.185 * squareSize);
			
			pane.getChildren().add(RookGraphics[i]);
		}
		
		//Knight Generation
		for (int i = 0; i < 4; i++) {
			if (i < 2) {
				knights[i] = new Knight();
			
				knights[i].xCoordOld = 1 + (5 * i);
				knights[i].yCoordOld = 7;
				knights[i].colour = true;
			
				master.gameBoard[knights[i].xCoordOld][knights[i].yCoordOld] = 2;
			
				KnightGraphics[i] = new ImageView(WhiteKnight);
			} else {
				knights[i] = new Knight();
			
				knights[i].xCoordOld = 1 + (5 * (i - 2));
				knights[i].yCoordOld = 0;
				knights[i].colour = false;
			
				master.gameBoard[knights[i].xCoordOld][knights[i].yCoordOld] = 8;
			
				KnightGraphics[i] = new ImageView(BlackKnight);
			}
			
			
			//Setting the position of the image
			KnightGraphics[i].setX(xOffset + (knights[i].xCoordOld * squareSize) - (0.082 * squareSize)); 
			KnightGraphics[i].setY(yOffset + (knights[i].yCoordOld * squareSize) - (0.02 * squareSize)); 
			KnightGraphics[i].setFitHeight(squareSize);
			KnightGraphics[i].setFitWidth(1.185 * squareSize);
			
			pane.getChildren().add(KnightGraphics[i]);
		}
		
		//Bishop Generation
		for (int i = 0; i < 4; i++) {
			if (i < 2) {
				bishops[i] = new Bishop();
			
				bishops[i].xCoordOld = 2 + (3 * i);
				bishops[i].yCoordOld = 7;
				bishops[i].colour = true;
				
				master.gameBoard[bishops[i].xCoordOld][bishops[i].yCoordOld] = 3;
				
				BishopGraphics[i] = new ImageView(WhiteBishop);
			} else {
				bishops[i] = new Bishop();
			
				bishops[i].xCoordOld = 2 + (3 * (i - 2));
				bishops[i].yCoordOld = 0;
				bishops[i].colour = false;
				
				master.gameBoard[bishops[i].xCoordOld][bishops[i].yCoordOld] = 9;
				
				BishopGraphics[i] = new ImageView(BlackBishop);
			}
			
			
			//Setting the position of the image
			BishopGraphics[i].setX(xOffset + (bishops[i].xCoordOld * squareSize) - (0.082 * squareSize)); 
			BishopGraphics[i].setY(yOffset + (bishops[i].yCoordOld * squareSize) - (0.02 * squareSize)); 
			BishopGraphics[i].setFitHeight(squareSize);
			BishopGraphics[i].setFitWidth(1.185 * squareSize);
			
			pane.getChildren().add(BishopGraphics[i]);
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
				
				KingGraphics[i] = new ImageView(WhiteKing);
			} else {
				kings[i] = new King();
			
				kings[i].xCoordOld = 4;
				kings[i].yCoordOld = 0;
				kings[i].colour = false;
				
				master.gameBoard[kings[i].xCoordOld][kings[i].yCoordOld] = 12;
				
				KingGraphics[i] = new ImageView(BlackKing);
			}
			
			
			//Setting the position of the image
			KingGraphics[i].setX(xOffset + (kings[i].xCoordOld * squareSize) - (0.092 * squareSize)); 
			KingGraphics[i].setY(yOffset + (kings[i].yCoordOld * squareSize) - (0.03 * squareSize)); 
			KingGraphics[i].setFitHeight(squareSize);
			KingGraphics[i].setFitWidth(1.185 * squareSize);
			
			pane.getChildren().add(KingGraphics[i]);
		}
		*/
		/****************************************
		Pawn Movement
		*****************************************/
		/*
		for (int b = 0; b < 16; b++) {
			
			int i = b;
			
			PawnGraphics[i].setOnMousePressed(new EventHandler <MouseEvent>()
			{
				public void handle(MouseEvent event)
				{
					event.setDragDetect(true);
				}
			});
			
			PawnGraphics[i].setOnMouseReleased(new EventHandler <MouseEvent>()
			{
				public void handle(MouseEvent event)
				{
					
					double xAxis = Math.floor((PawnGraphics[i].getX() + (squareSize/2) - xOffset)/squareSize);
					double yAxis = Math.floor((PawnGraphics[i].getY() + (squareSize/2) - yOffset)/squareSize);
					
					int xint = (int)xAxis;
					int yint = (int)yAxis;
					
					pawns[i].xCoordNew = xint;
					pawns[i].yCoordNew = yint;
					
					if (pawns[i].pawnMove(master.gameBoard)) {
						PawnGraphics[i].setX(xOffset + (xAxis * squareSize) - (0.092 * squareSize));
						PawnGraphics[i].setY(yOffset + (yAxis * squareSize) - (0.06 * squareSize));
						
						int x = (int)xAxis;
						int y = (int)yAxis;
						
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
						PawnGraphics[i].setX(xOffset - (0.092 * squareSize) + (pawns[i].xCoordOld * squareSize));
						PawnGraphics[i].setY(yOffset - (0.06 * squareSize) + (pawns[i].yCoordOld * squareSize));
					}
				}
			});
			
			PawnGraphics[i].setOnMouseDragged(new EventHandler <MouseEvent>()
			{
				public void handle(MouseEvent event)
				{
					double mouseX = event.getX();
					double mouseY = event.getY();
					
					PawnGraphics[i].setX(mouseX - (squareSize/2));
					PawnGraphics[i].setY(mouseY - (squareSize/2));
					
					event.setDragDetect(false);
				}
			});
		}
		
		/****************************************
		Rooks, Knights, Bishops Movement
		*****************************************/
		/*
		for (int b = 0; b < 4; b++) {
			int i = b;
			
			//Rooks*******************************************
			
			RookGraphics[i].setOnMousePressed(new EventHandler <MouseEvent>()
			{
				public void handle(MouseEvent event)
				{
					event.setDragDetect(true);
				}
			});
			
			RookGraphics[i].setOnMouseReleased(new EventHandler <MouseEvent>()
			{
				public void handle(MouseEvent event)
				{
					
					double xAxis = Math.floor((RookGraphics[i].getX() + (squareSize/2) - xOffset)/squareSize);
					double yAxis = Math.floor((RookGraphics[i].getY() + (squareSize/2) - yOffset)/squareSize);
					
					int xint = (int)xAxis;
					int yint = (int)yAxis;
					
					rooks[i].xCoordNew = xint;
					rooks[i].yCoordNew = yint;
					
					
					if (rooks[i].rookMove(master.gameBoard)) {
						RookGraphics[i].setX(xOffset + (xAxis * squareSize) - (0.092 * squareSize));
						RookGraphics[i].setY(yOffset + (yAxis * squareSize) - (0.06 * squareSize));
						
						int x = (int)xAxis;
						int y = (int)yAxis;
						
						master.gameBoard[rooks[i].xCoordOld][rooks[i].yCoordOld] = 0;
						
						if (rooks[i].colour) {
							master.gameBoard[rooks[i].xCoordNew][rooks[i].yCoordNew] = 4;
						} else {
							master.gameBoard[rooks[i].xCoordNew][rooks[i].yCoordNew] = 10;
						}
						
						rooks[i].xCoordOld = x;
						rooks[i].yCoordOld = y;
						
					} else {
						RookGraphics[i].setX(xOffset - (0.092 * squareSize) + (rooks[i].xCoordOld * squareSize));
						RookGraphics[i].setY(yOffset - (0.06 * squareSize) + (rooks[i].yCoordOld * squareSize));
					}
				}
			});
			
			RookGraphics[i].setOnMouseDragged(new EventHandler <MouseEvent>()
			{
				public void handle(MouseEvent event)
				{
					double mouseX = event.getX();
					double mouseY = event.getY();
					
					RookGraphics[i].setX(mouseX - (squareSize/2));
					RookGraphics[i].setY(mouseY - (squareSize/2));
					
					event.setDragDetect(false);
				}
			});
			
			//Knights*******************************************
			
			KnightGraphics[i].setOnMousePressed(new EventHandler <MouseEvent>()
			{
				public void handle(MouseEvent event)
				{
					event.setDragDetect(true);
				}
			});
			
			KnightGraphics[i].setOnMouseReleased(new EventHandler <MouseEvent>()
			{
				public void handle(MouseEvent event)
				{
					
					double xAxis = Math.floor((KnightGraphics[i].getX() + (squareSize/2) - xOffset)/squareSize);
					double yAxis = Math.floor((KnightGraphics[i].getY() + (squareSize/2) - yOffset)/squareSize);
					
					int xint = (int)xAxis;
					int yint = (int)yAxis;
					
					knights[i].xCoordNew = xint;
					knights[i].yCoordNew = yint;
					
					if (knights[i].knightMove(master.gameBoard)) {
						KnightGraphics[i].setX(xOffset + (xAxis * squareSize) - (0.092 * squareSize));
						KnightGraphics[i].setY(yOffset + (yAxis * squareSize) - (0.06 * squareSize));
						
						int x = (int)xAxis;
						int y = (int)yAxis;
						
						master.gameBoard[knights[i].xCoordOld][knights[i].yCoordOld] = 0;
						
						if (knights[i].colour) {
							master.gameBoard[knights[i].xCoordNew][knights[i].yCoordNew] = 2;
						} else {
							master.gameBoard[knights[i].xCoordNew][knights[i].yCoordNew] = 8;
						}
						
						knights[i].xCoordOld = x;
						knights[i].yCoordOld = y;
					} else {
						KnightGraphics[i].setX(xOffset - (0.092 * squareSize) + (knights[i].xCoordOld * squareSize));
						KnightGraphics[i].setY(yOffset - (0.06 * squareSize) + (knights[i].yCoordOld * squareSize));
					}
				}
			});
			
			KnightGraphics[i].setOnMouseDragged(new EventHandler <MouseEvent>()
			{
				public void handle(MouseEvent event)
				{
					double mouseX = event.getX();
					double mouseY = event.getY();
					
					KnightGraphics[i].setX(mouseX - (squareSize/2));
					KnightGraphics[i].setY(mouseY - (squareSize/2));
					
					event.setDragDetect(false);
				}
			});
			
			//Bishops*******************************************
			
			BishopGraphics[i].setOnMousePressed(new EventHandler <MouseEvent>()
			{
				public void handle(MouseEvent event)
				{
					event.setDragDetect(true);
				}
			});
			
			BishopGraphics[i].setOnMouseReleased(new EventHandler <MouseEvent>()
			{
				public void handle(MouseEvent event)
				{
					
					double xAxis = Math.floor((BishopGraphics[i].getX() + (squareSize/2) - xOffset)/squareSize);
					double yAxis = Math.floor((BishopGraphics[i].getY() + (squareSize/2) - yOffset)/squareSize);
					
					int xint = (int)xAxis;
					int yint = (int)yAxis;
					
					bishops[i].xCoordNew = xint;
					bishops[i].yCoordNew = yint;
					
					if (bishops[i].bishopMove(master.gameBoard)) {
						BishopGraphics[i].setX(xOffset + (xAxis * squareSize) - (0.092 * squareSize));
						BishopGraphics[i].setY(yOffset + (yAxis * squareSize) - (0.06 * squareSize));
						
						int x = (int)xAxis;
						int y = (int)yAxis;
						
						master.gameBoard[bishops[i].xCoordOld][bishops[i].yCoordOld] = 0;
						
						if (bishops[i].colour) {
							master.gameBoard[bishops[i].xCoordNew][bishops[i].yCoordNew] = 3;
						} else {
							master.gameBoard[bishops[i].xCoordNew][bishops[i].yCoordNew] = 9;
						}
						
						bishops[i].xCoordOld = x;
						bishops[i].yCoordOld = y;
					} else {
						BishopGraphics[i].setX(xOffset - (0.092 * squareSize) + (bishops[i].xCoordOld * squareSize));
						BishopGraphics[i].setY(yOffset - (0.06 * squareSize) + (bishops[i].yCoordOld * squareSize));
					}
				}
			});
			
			BishopGraphics[i].setOnMouseDragged(new EventHandler <MouseEvent>()
			{
				public void handle(MouseEvent event)
				{
					double mouseX = event.getX();
					double mouseY = event.getY();
					
					BishopGraphics[i].setX(mouseX - (squareSize/2));
					BishopGraphics[i].setY(mouseY - (squareSize/2));
					
					event.setDragDetect(false);
				}
			});
		}
		
		/****************************************
		Queens, Kings Movement
		*****************************************/
		/*
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
						
						master.gameBoard[queens[i].xCoordOld][queens[i].yCoordOld] = 0;
						
						if (queens[i].colour) {
							master.gameBoard[queens[i].xCoordNew][queens[i].yCoordNew] = 5;
						} else {
							master.gameBoard[queens[i].xCoordNew][queens[i].yCoordNew] = 11;
						}
						
						queens[i].xCoordOld = x;
						queens[i].yCoordOld = y;
						
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
			
			KingGraphics[i].setOnMousePressed(new EventHandler <MouseEvent>()
			{
				public void handle(MouseEvent event)
				{
					event.setDragDetect(true);
				}
			});
			
			KingGraphics[i].setOnMouseReleased(new EventHandler <MouseEvent>()
			{
				public void handle(MouseEvent event)
				{
					
					double xAxis = Math.floor((KingGraphics[i].getX() + (squareSize/2) - xOffset)/squareSize);
					double yAxis = Math.floor((KingGraphics[i].getY() + (squareSize/2) - yOffset)/squareSize);
					
					int xint = (int)xAxis;
					int yint = (int)yAxis;
					
					kings[i].xCoordNew = xint;
					kings[i].yCoordNew = yint;
					
					if (kings[i].kingMove(master.gameBoard)) {
						KingGraphics[i].setX(xOffset + (xAxis * squareSize) - (0.092 * squareSize));
						KingGraphics[i].setY(yOffset + (yAxis * squareSize) - (0.06 * squareSize));
						
						int x = (int)xAxis;
						int y = (int)yAxis;
						
						master.gameBoard[kings[i].xCoordOld][kings[i].yCoordOld] = 0;
						
						if (kings[i].colour) {
							master.gameBoard[kings[i].xCoordNew][kings[i].yCoordNew] = 6;
						} else {
							master.gameBoard[kings[i].xCoordNew][kings[i].yCoordNew] = 12;
						}
						
						kings[i].xCoordOld = x;
						kings[i].yCoordOld = y;
						
					} else {
						KingGraphics[i].setX(xOffset - (0.092 * squareSize) + (kings[i].xCoordOld * squareSize));
						KingGraphics[i].setY(yOffset - (0.06 * squareSize) + (kings[i].yCoordOld * squareSize));
					}
				}
			});
			
			KingGraphics[i].setOnMouseDragged(new EventHandler <MouseEvent>()
			{
				public void handle(MouseEvent event)
				{
					double mouseX = event.getX();
					double mouseY = event.getY();
					
					KingGraphics[i].setX(mouseX - (squareSize/2));
					KingGraphics[i].setY(mouseY - (squareSize/2));
					
					event.setDragDetect(false);
				}
			});
		}*/
		
		myStage.show();
	}
}