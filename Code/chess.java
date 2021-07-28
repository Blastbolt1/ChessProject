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
import javafx.scene.shape.Shape;
import javafx.scene.shape.Circle;
import javafx.stage.Stage; 

// Main class
class Game {
	double xOffset;
	double yOffset;
	double imageXOffset;
	double imageYOffset;
	double squareSize;
	boolean turn;
	boolean isCheck;
	boolean[][] possibilityBoard;
	Rectangle[][] chessBoard;
	ImageView[][] pieceGraphicsMaster;
	logicContainer pieceLogic;
	Piece[][] arrayLogic;
	Circle[][] squareSelect; 
	Pane pane;
	Image WhitePawn;
	Image WhiteRook;
	Image WhiteKnight;
	Image WhiteBishop;
	Image WhiteQueen;
	Image WhiteKing;
	Image BlackPawn;
	Image BlackRook;
	Image BlackKnight;
	Image BlackBishop;
	Image BlackQueen;
	Image BlackKing;
	Image CheckGraphics;
	ImageView visualCheck;
	
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
	
	public void imagePos(ImageView image, int x, int y, double xOff, double yOff, double Height, double Width) {
		image.setX(xOffset + (x * squareSize) - (xOff * squareSize)); 
		image.setY(yOffset + (y * squareSize) - (yOff * squareSize)); 
		image.setFitHeight(Height);
		image.setFitWidth(Width);
	}
	
	public void initCircle(Circle circle, int x, int y, double size, double opacity) {
		double xPos = xOffset + (x * squareSize) + (squareSize/2);
		double yPos = yOffset + (y * squareSize) + (squareSize/2);
		
		circle = new Circle(xPos, yPos, size);
		circle.setOpacity(opacity);
	}
	
	public void initSquareSelection() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				//initCircle(this.squareSelect[j][i], j, i, 17, 0.35);
				double xPos = xOffset + (j * squareSize) + (squareSize/2);
				double yPos = yOffset + (i * squareSize) + (squareSize/2);
				
				this.squareSelect[j][i] = new Circle(xPos, yPos, 17);
				this.squareSelect[j][i].setOpacity(0.35);
			}
		}
	}
	
	public void initGame(int board[][]) throws FileNotFoundException {
		//Assigning images
		WhitePawn = new Image(new FileInputStream("E:\\Coding\\ChessProject\\Graphics\\White\\WhitePawn.png"));
		WhiteRook = new Image(new FileInputStream("E:\\Coding\\ChessProject\\Graphics\\White\\WhiteRook.png"));
		WhiteKnight = new Image(new FileInputStream("E:\\Coding\\ChessProject\\Graphics\\White\\WhiteKnight.png"));
		WhiteBishop = new Image(new FileInputStream("E:\\Coding\\ChessProject\\Graphics\\White\\WhiteBishop.png"));
		WhiteQueen = new Image(new FileInputStream("E:\\Coding\\ChessProject\\Graphics\\White\\WhiteQueen.png"));
		WhiteKing = new Image(new FileInputStream("E:\\Coding\\ChessProject\\Graphics\\White\\WhiteKing.png"));
		
		BlackPawn = new Image(new FileInputStream("E:\\Coding\\ChessProject\\Graphics\\Black\\BlackPawn.png"));
		BlackRook = new Image(new FileInputStream("E:\\Coding\\ChessProject\\Graphics\\Black\\BlackRook.png"));
		BlackKnight = new Image(new FileInputStream("E:\\Coding\\ChessProject\\Graphics\\Black\\BlackKnight.png"));
		BlackBishop = new Image(new FileInputStream("E:\\Coding\\ChessProject\\Graphics\\Black\\BlackBishop.png"));
		BlackQueen = new Image(new FileInputStream("E:\\Coding\\ChessProject\\Graphics\\Black\\BlackQueen.png"));
		BlackKing = new Image(new FileInputStream("E:\\Coding\\ChessProject\\Graphics\\Black\\BlackKing.png"));
		
		CheckGraphics = new Image(new FileInputStream("E:\\Coding\\ChessProject\\Graphics\\blurCircle.png"));

		this.pieceGraphicsMaster = new ImageView[8][8];
		this.pieceLogic = new logicContainer();
		this.arrayLogic = new Piece[8][8];
		this.possibilityBoard = new boolean[8][8];
		this.squareSelect = new Circle[8][8];
		this.isCheck = false;
		
		int pawnTrack = 0;
		int knightTrack = 0;
		int bishopTrack = 0;
		int rookTrack = 0;
		int queenTrack = 0;
		int kingTrack = 0;
		int imageTrack = 0;
		
		this.visualCheck = new ImageView(CheckGraphics);
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				//int x = j;
				//int y = i;
				
				switch (board[j][i]) {
					case 1:
						this.pieceGraphicsMaster[j][i] = new ImageView(WhitePawn);
						imagePos(this.pieceGraphicsMaster[j][i], j, i, 0.092, 0.07, squareSize, 1.185 * squareSize);
						
						this.pieceLogic.pawns[pawnTrack] = new Pawn(j, i, true, true, false);
						pawnTrack++;
						imageTrack++;
						
						this.arrayLogic[j][i] = new Pawn(j, i, true, true, false);
						
						break;               
					case 2:
						this.pieceGraphicsMaster[j][i] = new ImageView(WhiteKnight);
						imagePos(this.pieceGraphicsMaster[j][i], j, i, 0.082, 0.02, squareSize, 1.185 * squareSize);
						
						this.pieceLogic.knights[knightTrack] = new Knight(j, i, true, false);
						knightTrack++;
						imageTrack++;
						
						this.arrayLogic[j][i] = new Knight(j, i, true, false);
						
						break;               
					case 3:                  
						this.pieceGraphicsMaster[j][i] = new ImageView(WhiteBishop);
						imagePos(this.pieceGraphicsMaster[j][i], j, i, 0.082, 0.02, squareSize, 1.185 * squareSize);
						
						this.pieceLogic.bishops[bishopTrack] = new Bishop(j, i, true, false);
						bishopTrack++;
						imageTrack++;
						
						this.arrayLogic[j][i] = new Bishop(j, i, true, false);
						
						break;               
					case 4:                  
						this.pieceGraphicsMaster[j][i] = new ImageView(WhiteRook);
						imagePos(this.pieceGraphicsMaster[j][i], j, i, 0.1, 0.06, squareSize, 1.185 * squareSize);
						
						this.pieceLogic.rooks[rookTrack] = new Rook(j, i, true, false);
						rookTrack++;
						imageTrack++;
						
						this.arrayLogic[j][i] = new Rook(j, i, true, false);
						
						break;               
					case 5:                  
						this.pieceGraphicsMaster[j][i] = new ImageView(WhiteQueen);
						imagePos(this.pieceGraphicsMaster[j][i], j, i, 0.092, 0.03, squareSize, 1.185 * squareSize);
						
						this.pieceLogic.queens[queenTrack] = new Queen(j, i, true, false);
						queenTrack++;
						imageTrack++;
						
						this.arrayLogic[j][i] = new Queen(j, i, true, false);
						
						break;               
					case 6:                  
						this.pieceGraphicsMaster[j][i] = new ImageView(WhiteKing);
						imagePos(this.pieceGraphicsMaster[j][i], j, i, 0.092, 0.03, squareSize, 1.185 * squareSize);
						
						this.pieceLogic.kings[kingTrack] = new King(j, i, true, false);
						kingTrack++;
						imageTrack++;
						
						this.arrayLogic[j][i] = new King(j, i, true, false);
						
						break;               
					case 7:                  
						this.pieceGraphicsMaster[j][i] = new ImageView(BlackPawn);
						imagePos(this.pieceGraphicsMaster[j][i], j, i, 0.092, 0.07, squareSize, 1.185 * squareSize);

						this.pieceLogic.pawns[pawnTrack] = new Pawn(j, i, true, false, false);
						pawnTrack++;
						imageTrack++;
						
						this.arrayLogic[j][i] = new Pawn(j, i, true, false, false);
						
						break;               
					case 8:                  
						this.pieceGraphicsMaster[j][i] = new ImageView(BlackKnight);
						imagePos(this.pieceGraphicsMaster[j][i], j, i, 0.082, 0.02, squareSize, 1.185 * squareSize);
						
						this.pieceLogic.knights[knightTrack] = new Knight(j, i, false, false);
						knightTrack++;
						imageTrack++;
						
						this.arrayLogic[j][i] = new Knight(j, i, false, false);
						
						break;               
					case 9:                  
						this.pieceGraphicsMaster[j][i] = new ImageView(BlackBishop);
						imagePos(this.pieceGraphicsMaster[j][i], j, i, 0.082, 0.02, squareSize, 1.185 * squareSize);
						
						this.pieceLogic.bishops[bishopTrack] = new Bishop(j, i, false, false);
						bishopTrack++;
						imageTrack++;
						
						this.arrayLogic[j][i] = new Bishop(j, i, false, false);
						
						break;
					case 10:              
						this.pieceGraphicsMaster[j][i] = new ImageView(BlackRook);
						imagePos(this.pieceGraphicsMaster[j][i], j, i, 0.1, 0.06, squareSize, 1.185 * squareSize);
						
						this.pieceLogic.rooks[rookTrack] = new Rook(j, i, false, false);
						rookTrack++;
						imageTrack++;
						
						this.arrayLogic[j][i] = new Rook(j, i, false, false);
						
						break;               
					case 11:                 
						this.pieceGraphicsMaster[j][i] = new ImageView(BlackQueen);
						imagePos(this.pieceGraphicsMaster[j][i], j, i, 0.092, 0.03, squareSize, 1.185 * squareSize);
						
						this.pieceLogic.queens[queenTrack] = new Queen(j, i, false, false);
						queenTrack++;
						imageTrack++;
						
						this.arrayLogic[j][i] = new Queen(j, i, false, false);
						
						break;               
					case 12:                 
						this.pieceGraphicsMaster[j][i] = new ImageView(BlackKing);
						imagePos(this.pieceGraphicsMaster[j][i], j, i, 0.092, 0.03, squareSize, 1.185 * squareSize);
						
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
			
			boolean isPromotion = false;
			
			if (board[logic.xCoordNew][logic.yCoordNew] > 0) {
				this.pane.getChildren().remove(this.pieceGraphicsMaster[logic.xCoordNew][logic.yCoordNew]);
			}
			
			if (board[logic.xCoordOld][logic.yCoordOld] == 1 || board[logic.xCoordOld][logic.yCoordOld] == 7) {
				Pawn testPawn = new Pawn(logic.xCoordNew, logic.yCoordNew, logic.firstMove, logic.colour, false);
				pieceSelect.firstMove = false;
				
				if (testPawn.isEndRank()) {
					System.out.println("ayo");
					isPromotion = true;
					
					this.pane.getChildren().remove(this.pieceGraphicsMaster[logic.xCoordOld][logic.yCoordOld]);
					
					board[logic.xCoordOld][logic.yCoordOld] = 0;
					
					if (testPawn.colour) {
						this.pieceGraphicsMaster[logic.xCoordNew][logic.yCoordNew] = new ImageView(WhiteQueen);
						
						board[logic.xCoordNew][logic.yCoordNew] = 5;
						
						this.arrayLogic[logic.xCoordNew][logic.yCoordNew] = new Queen(logic.xCoordNew, logic.yCoordNew, true, false);

					} else {
						this.pieceGraphicsMaster[logic.xCoordNew][logic.yCoordNew] = new ImageView(BlackQueen);
						
						board[logic.xCoordNew][logic.yCoordNew] = 11;
						
						this.arrayLogic[logic.xCoordNew][logic.yCoordNew] = new Queen(logic.xCoordNew, logic.yCoordNew, false, false);
					}
					imagePos(this.pieceGraphicsMaster[logic.xCoordNew][logic.yCoordNew], logic.xCoordNew, logic.yCoordNew, 0.092, 0.03, squareSize, 1.185 * squareSize);
					this.pane.getChildren().add(this.pieceGraphicsMaster[logic.xCoordNew][logic.yCoordNew]);
					
					this.arrayLogic[logic.xCoordOld][logic.yCoordOld] = new Piece();
				}
			}
			
			if (isPromotion == false) {
				this.pieceGraphicsMaster[logic.xCoordNew][logic.yCoordNew] = this.pieceGraphicsMaster[logic.xCoordOld][logic.yCoordOld];
				this.pieceGraphicsMaster[logic.xCoordOld][logic.yCoordOld] = new ImageView();
				this.pane.getChildren().remove(this.pieceGraphicsMaster[logic.xCoordOld][logic.yCoordOld]);
				
				this.arrayLogic[logic.xCoordNew][logic.yCoordNew] = this.arrayLogic[logic.xCoordOld][logic.yCoordOld];
				this.arrayLogic[logic.xCoordOld][logic.yCoordOld] = new Piece();
				
				board[logic.xCoordOld][logic.yCoordOld] = 0;
				board[logic.xCoordNew][logic.yCoordNew] = newSquare;
				
				this.arrayLogic[logic.xCoordNew][logic.yCoordNew].xCoordOld = this.arrayLogic[logic.xCoordNew][logic.yCoordNew].xCoordNew;
				this.arrayLogic[logic.xCoordNew][logic.yCoordNew].yCoordOld = this.arrayLogic[logic.xCoordNew][logic.yCoordNew].yCoordNew;
			}
		} else {
			graphic.setX(this.xOffset - (xOff * this.squareSize) + (logic.xCoordOld * this.squareSize));
			graphic.setY(this.yOffset - (yOff * this.squareSize) + (logic.yCoordOld * this.squareSize));
		}
	}
	
	public void printBoard(int[][] board) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				System.out.print(board[j][i] + " ");
			}
			System.out.println();
		}
	}
	
}

public class chess extends Application {
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
		Game.initGame(logicBoard.logicBoard);
		
		myStage.setResizable(true);

		myStage.setTitle("Chess");

		Scene myScene = new Scene(Game.pane, 850, 850);

		myStage.setScene(myScene);
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Game.pane.getChildren().add(Game.chessBoard[j][i]);
			}
		}
		
		//Game.pane.getChildren().add(Game.visualCheck);
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Game.pane.getChildren().add(Game.pieceGraphicsMaster[j][i]);
			}
		}
		
		Game.initSquareSelection();
		
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
				
				Game.pane.getChildren().remove(Game.pieceGraphicsMaster[x][y]);
				Game.pane.getChildren().add(Game.pieceGraphicsMaster[x][y]);
				
				System.out.println("This Piece can move to the following squares:");
				
				if (x > -1 && x < 8 && y > -1 && y < 8) {
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
										Game.pane.getChildren().add(Game.squareSelect[j][i]);
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
										Game.pane.getChildren().add(Game.squareSelect[j][i]);
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
										Game.pane.getChildren().add(Game.squareSelect[j][i]);
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
										Game.pane.getChildren().add(Game.squareSelect[j][i]);
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
										Game.pane.getChildren().add(Game.squareSelect[j][i]);
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
										Game.pane.getChildren().add(Game.squareSelect[j][i]);
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
						
						int checkX = 0;
						int checkY = 0;
						
						if (Game.possibilityBoard[xint][yint] == false) {
							canMove = false;
						}
						
						for (int i = 0; i < 8; i++) {
							for (int j = 0; j < 8; j++) {
								Game.pane.getChildren().remove(Game.squareSelect[j][i]);
							}
						}
						
						Game.movePiece(canMove, Game.pieceGraphicsMaster[x][y], Game.arrayLogic[x][y], logicBoard.logicBoard, xint, yint, logicBoard.logicBoard[x][y], Game.imageXOffset, Game.imageYOffset);
						
						if (canMove) {
							Game.pane.getChildren().remove(Game.visualCheck);
							
							switch (logicBoard.logicBoard[xint][yint]) {
								case 1:
								case 7:
									Pawn myPawn = new Pawn(xint, yint, Game.arrayLogic[xint][yint].firstMove, Game.arrayLogic[x][y].colour, false);
									
									for (int i = 0; i < 8; i++) {
										for (int j = 0; j < 8; j++) {
											
											myPawn.xCoordNew = j;
											myPawn.yCoordNew = i;
											
											if (myPawn.Move(logicBoard.logicBoard, Game.turn)) {
												Game.possibilityBoard[j][i] = true;
												
												if (Game.arrayLogic[xint][yint].colour) {
													if (Game.possibilityBoard[j][i] && logicBoard.logicBoard[j][i] == 12) {
														Game.isCheck = true;
														
														checkX = j;
														checkY = i;
													}
												} else {
													if (Game.possibilityBoard[j][i] && logicBoard.logicBoard[j][i] == 6) {
														Game.isCheck = true;
														
														checkX = j;
														checkY = i;
													}
												}
												
											} else {
												Game.possibilityBoard[j][i] = false;
											}
										}
									}
									
									break;
								case 2:
								case 8:
									Knight myKnight = new Knight(xint, yint, Game.arrayLogic[xint][yint].colour, false);
			
									for (int i = 0; i < 8; i++) {
										for (int j = 0; j < 8; j++) {
											
											myKnight.xCoordNew = j;
											myKnight.yCoordNew = i;
											
											if (myKnight.Move(logicBoard.logicBoard, Game.turn)) {
												Game.possibilityBoard[j][i] = true;
												
												if (Game.arrayLogic[xint][yint].colour) {
													if (Game.possibilityBoard[j][i] && logicBoard.logicBoard[j][i] == 12) {
														Game.isCheck = true;
														
														checkX = j;
														checkY = i;
													}
												} else {
													if (Game.possibilityBoard[j][i] && logicBoard.logicBoard[j][i] == 6) {
														Game.isCheck = true;
														
														checkX = j;
														checkY = i;
													}
												}
												
											} else {
												Game.possibilityBoard[j][i] = false;
											}
										}
									}
									
									break;
								case 3:
								case 9:
									Bishop myBishop = new Bishop(xint, yint, Game.arrayLogic[xint][yint].colour, false);
			
									for (int i = 0; i < 8; i++) {
										for (int j = 0; j < 8; j++) {
											
											myBishop.xCoordNew = j;
											myBishop.yCoordNew = i;
											
											if (myBishop.Move(logicBoard.logicBoard, Game.turn)) {
												Game.possibilityBoard[j][i] = true;
												
												if (Game.arrayLogic[xint][yint].colour) {
													if (Game.possibilityBoard[j][i] && logicBoard.logicBoard[j][i] == 12) {
														Game.isCheck = true;
														
														checkX = j;
														checkY = i;
													}
												} else {
													if (Game.possibilityBoard[j][i] && logicBoard.logicBoard[j][i] == 6) {
														Game.isCheck = true;
														
														checkX = j;
														checkY = i;
													}
												}
												
											} else {
												Game.possibilityBoard[j][i] = false;
											}
										}
									}
									
									break;
								case 4:
								case 10:
									Rook myRook = new Rook(xint, yint, Game.arrayLogic[xint][yint].colour, false);
									
									for (int i = 0; i < 8; i++) {
										for (int j = 0; j < 8; j++) {
											
											myRook.xCoordNew = j;
											myRook.yCoordNew = i;
											
											if (myRook.Move(logicBoard.logicBoard, Game.turn)) {
												Game.possibilityBoard[j][i] = true;
												
												if (Game.arrayLogic[xint][yint].colour) {
													if (Game.possibilityBoard[j][i] && logicBoard.logicBoard[j][i] == 12) {
														Game.isCheck = true;
														
														checkX = j;
														checkY = i;
													}
												} else {
													if (Game.possibilityBoard[j][i] && logicBoard.logicBoard[j][i] == 6) {
														Game.isCheck = true;
														
														checkX = j;
														checkY = i;
													}
												}
												
											} else {
												Game.possibilityBoard[j][i] = false;
											}
										}
									}
									
									break;
								case 5:
								case 11:
									Queen myQueen = new Queen(xint, yint, Game.arrayLogic[xint][yint].colour, false);
									
									System.out.println("Colour: " + myQueen.colour);
									System.out.println("Turn: " + Game.turn);
									
									for (int i = 0; i < 8; i++) {
										for (int j = 0; j < 8; j++) {
														
											myQueen.xCoordNew = j;
											myQueen.yCoordNew = i;
											
											//System.out.println("OldX: " + myQueen.xCoordOld);
											//System.out.println("OldY: " + myQueen.yCoordOld);
											//System.out.println("NewX: " + myQueen.xCoordNew);
											//System.out.println("NewY: " + myQueen.yCoordNew);
											
											
											if (myQueen.Move(logicBoard.logicBoard, Game.turn)) {
												Game.possibilityBoard[j][i] = true;
												System.out.println("Check check");
												if (Game.arrayLogic[xint][yint].colour) {
													
													System.out.println("White");
													
													if (Game.possibilityBoard[j][i] && logicBoard.logicBoard[j][i] == 12) {
														Game.isCheck = true;
														
														checkX = j;
														checkY = i;
													}
												} else {
													System.out.println("Black");
													if (Game.possibilityBoard[j][i] && logicBoard.logicBoard[j][i] == 6) {
														Game.isCheck = true;
														
														checkX = j;
														checkY = i;
													}
												}
												
											} else {
												Game.possibilityBoard[j][i] = false;
											}
										}
									}						
									
									break;
								case 6:
								case 12:
									King myKing = new King(x, y, Game.arrayLogic[x][y].colour, false);
									
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
							
							Game.turn = !Game.turn;
						}
						
						
						if (Game.isCheck) {
							Game.imagePos(Game.visualCheck, checkX, checkY, 0.0005 * squareSize, 0.0005 * squareSize, 1.1 * squareSize, 1.1 * squareSize);
							Game.pane.getChildren().remove(Game.pieceGraphicsMaster[checkX][checkY]);
							Game.pane.getChildren().add(Game.visualCheck);
							Game.pane.getChildren().add(Game.pieceGraphicsMaster[checkX][checkY]);
							System.out.println("Ayo you in check");
							Game.isCheck = false;
						}
						
						
					}
				}
			}
		});
		
		myScene.setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {		
				selector.mouseX = event.getX();
				selector.mouseY = event.getY();
				
				int x = selector.xSelect;
				int y = selector.ySelect;
				
				if (x > -1 && x < 8 && y > -1 && y < 8) {
					if (logicBoard.logicBoard[selector.xSelect][selector.ySelect] > 0) {
						if (Game.turn == Game.arrayLogic[selector.xSelect][selector.ySelect].colour) {
							Game.pieceGraphicsMaster[selector.xSelect][selector.ySelect].setX(selector.mouseX - (squareSize/2));
							Game.pieceGraphicsMaster[selector.xSelect][selector.ySelect].setY(selector.mouseY - (squareSize/2));
						}
					}
				}
				
			}
		});
		
		myStage.show();
	}
}