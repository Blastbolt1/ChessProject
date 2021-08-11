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
						
						this.pieceLogic.rooks[rookTrack] = new Rook(j, i, true, false, true);
						rookTrack++;
						imageTrack++;
						
						this.arrayLogic[j][i] = new Rook(j, i, true, false, true);
						
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
						
						this.pieceLogic.kings[kingTrack] = new King(j, i, true, false, true);
						kingTrack++;
						imageTrack++;
						
						this.arrayLogic[j][i] = new King(j, i, true, false, true);
						System.out.println("King is on first move: " + this.arrayLogic[4][7].firstMove);
						
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
						
						this.pieceLogic.rooks[rookTrack] = new Rook(j, i, false, false, true);
						rookTrack++;
						imageTrack++;
						
						this.arrayLogic[j][i] = new Rook(j, i, false, false, true);
						
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
						
						this.pieceLogic.kings[kingTrack] = new King(j, i, false, false, true);
						kingTrack++;
						imageTrack++;
						
						this.arrayLogic[j][i] = new King(j, i, false, false, true);
						
						break;
					default:
						this.pieceGraphicsMaster[j][i] = new ImageView();
						
						break;
				}
			}
		}
	}
	
	public void movePiece(boolean moveCheck, ImageView graphic, Piece pieceSelect, int[][] board, double xAxis, double yAxis, int newSquare, double xOff, double yOff, boolean castling, int rookX, int rookY) {
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
			
			pieceSelect.firstMove = false;
			
			if (board[logic.xCoordOld][logic.yCoordOld] == 1 || board[logic.xCoordOld][logic.yCoordOld] == 7) {
				Pawn testPawn = new Pawn(logic.xCoordNew, logic.yCoordNew, logic.firstMove, logic.colour, false);
				
				
				if (testPawn.isEndRank()) {
					
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

			
			if (castling) {
				int xDif = logic.xCoordNew - logic.xCoordOld;
				int newRookX = 0;
				int newRookY = 0;
				
				if (xDif > 0) {
					newRookX = logic.xCoordNew - 1;
					newRookY = logic.yCoordNew;
				} else {
					newRookX = logic.xCoordNew + 1;
					newRookY = logic.yCoordNew;
				}
				
				System.out.println("Old Rook X: " + rookX);
				System.out.println("Old Rook Y: " + rookY);
				System.out.println("New Rook X: " + newRookX);
				System.out.println("New Rook Y: " + newRookY);
				
				this.pieceGraphicsMaster[4][7] = this.pieceGraphicsMaster[0][3];
				imagePos(this.pieceGraphicsMaster[4][7], 4, 7, 0.1, 0.06, squareSize, 1.185 * squareSize);
				this.pieceGraphicsMaster[rookX][rookY] = new ImageView();
				this.pane.getChildren().remove(this.pieceGraphicsMaster[0][7]);
				
				/*for (int i = 0; i < 8; i++) {
					this.pane.getChildren().remove(this.pieceGraphicsMaster[i][7]);
				}*/
				
				this.arrayLogic[newRookX][newRookY] = this.arrayLogic[rookX][rookY];
				this.arrayLogic[rookX][rookY] = new Piece();
				
				board[rookX][rookY] = 0;
				if (this.turn) {
					board[newRookX][newRookY] = 4;
				} else {
					board[newRookX][newRookY] = 10;
				}
				board[logic.xCoordOld][logic.yCoordOld] = 0;
				board[logic.xCoordNew][logic.yCoordNew] = newSquare;
				
				this.arrayLogic[newRookX][newRookY].xCoordOld = newRookX;
				this.arrayLogic[newRookX][newRookY].yCoordOld = newRookY;
				this.printBoard(board);
			}
			
			if (isPromotion == false) {
				this.pieceGraphicsMaster[logic.xCoordNew][logic.yCoordNew] = this.pieceGraphicsMaster[logic.xCoordOld][logic.yCoordOld];
				this.pieceGraphicsMaster[logic.xCoordOld][logic.yCoordOld] = new ImageView();
				this.pane.getChildren().remove(this.pieceGraphicsMaster[logic.xCoordOld][logic.yCoordOld]);
				//this.pane.getChildren().remove(this.pieceGraphicsMaster[4][0]);
				
				this.arrayLogic[logic.xCoordNew][logic.yCoordNew] = this.arrayLogic[logic.xCoordOld][logic.yCoordOld];
				this.arrayLogic[logic.xCoordOld][logic.yCoordOld] = new Piece();
				
				board[logic.xCoordOld][logic.yCoordOld] = 0;
				board[logic.xCoordNew][logic.yCoordNew] = newSquare;
				
				this.arrayLogic[logic.xCoordNew][logic.yCoordNew].xCoordOld = this.arrayLogic[logic.xCoordNew][logic.yCoordNew].xCoordNew;
				this.arrayLogic[logic.xCoordNew][logic.yCoordNew].yCoordOld = this.arrayLogic[logic.xCoordNew][logic.yCoordNew].yCoordNew;
				this.arrayLogic[logic.xCoordNew][logic.yCoordNew].firstMove = false;
				
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
	
	public int[] getRookCoords(int CoordID) {
		int x = 0;
		int y = 0;
		int[] vector = new int[2];
		
		switch (CoordID) {
			case 1:
				x = 0;
				y = 0;
				break;
			case 2:
				x = 7;
				y = 0;
				break;
			case 3:
				x = 0;
				y = 7;
				break;
			case 4:
				x = 7;
				y = 7;
				break;
			default:
				break;
		}
		
		vector[0] = x;
		vector[1] = y;
		
		return vector;
	}
	
	public boolean isRookFirstMove(int x, int y)
	{
		
		//System.out.println("Rook is on its first move? " + this.arrayLogic[x][y].firstMove);
		
		if (this.arrayLogic[x][y].firstMove) {
			//System.out.println("Rook has not moved");
			return true;
		} else {
			//System.out.println("Rook has moved");
			return false;
		}
	}
}

class Board {
	int[][] logicBoard;
	
	public void initState() {
		//x, y, 0 for empty square, 1-12 for piece types/colour. 1-6 is white, 7-12 is black
		//white pawn = 1
		//white knight = 2
		//white bishop = 3
		//white rook = 4
		//white queen = 5
		//white king = 6
		//add 6 to each for black pieces
		
		this.logicBoard = new int[8][8];
		
		logicBoard[0][0] = 10;
		logicBoard[1][0] = 8;
		logicBoard[2][0] = 9;
		logicBoard[3][0] = 11;
		logicBoard[4][0] = 12;
		logicBoard[5][0] = 9;
		logicBoard[6][0] = 8;
		logicBoard[7][0] = 10;
		
		logicBoard[0][7] = 4;
		logicBoard[1][7] = 2;
		logicBoard[2][7] = 3;
		logicBoard[3][7] = 5;
		logicBoard[4][7] = 6;
		logicBoard[5][7] = 3;
		logicBoard[6][7] = 2;
		logicBoard[7][7] = 4;
		
		for (int i = 0; i < 8; i++) {
			logicBoard[i][1] = 7;
			logicBoard[i][6] = 1;
		}
		
		logicBoard[4][6] = 0;
		logicBoard[4][4] = 1;
		logicBoard[4][3] = 7;
		logicBoard[4][1] = 0;
		logicBoard[6][7] = 0;
		logicBoard[5][5] = 2;
		logicBoard[6][0] = 0;
		logicBoard[5][2] = 8;
		logicBoard[5][7] = 0;
		logicBoard[2][4] = 3;
		logicBoard[1][0] = 0;
		logicBoard[2][2] = 8;
		
		logicBoard[1][7] = 0;
		logicBoard[2][7] = 0;
		//logicBoard[3][7] = 0;
	}
}

class Piece {
	int xCoordOld;
	int yCoordOld;
	int xCoordNew;
	int yCoordNew;
	int[][] gameBoard;
	boolean firstMove;
	boolean colour;
	boolean isCaptured;
	boolean isCheckImportant;
	boolean isKing;
	
	public boolean Lines() {
		if (xCoordNew == xCoordOld) {
			if (yCoordNew == yCoordOld) {
				return false;
			} else {
				return true;
			}
		} else if (yCoordNew == yCoordOld) {
			if (xCoordNew == xCoordOld) {
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}
	
	public boolean Diagonals() {
		int xDif = Math.abs(xCoordNew - xCoordOld);
		int yDif = Math.abs(yCoordNew - yCoordOld);
		
		if (xDif == yDif) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean colourOnSquare(boolean colour, int[][] squares, int x, int y) {
		if (colour) {
			//System.out.println("X: " + x + " Y:" + y);
			
			if (squares[x][y] > 6 || squares[x][y] == 0) {
				return true;
			} else {
				return false;
			}
		} else {
			if (squares[x][y] < 7) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	public boolean canMove(int xOld, int xNew, int yOld, int yNew, int[][] squares, boolean colour, boolean turn, boolean isCheckImportant, boolean isKing) {
		
		xCoordOld = xOld;
		xCoordNew = xNew;
		yCoordOld = yOld;
		yCoordNew = yNew;
		
		int xDifference = xCoordNew - xCoordOld;
		int yDifference = yCoordNew - yCoordOld;
		
		if (colour == turn) {
			boolean checkRetVal = false;
			checkHandler checkHandler = new checkHandler();
			
			if (isCheckImportant) {
				int[][] testBoard = new int[8][8];
			
				for (int y = 0; y < 8; y++) {
					for (int x = 0; x < 8; x++) {
						testBoard[x][y] = squares[x][y];
					}
				}
				
				testBoard[xCoordNew][yCoordNew] = testBoard[xCoordOld][yCoordOld];
				testBoard[xCoordOld][yCoordOld] = 0;	
				
				//checkRetVal = checkHandler.isKingInCheck(turn, testBoard);
				checkRetVal = checkHandler.isPreventedByCheck(turn, testBoard, isKing);
			}
			
			if (!checkRetVal) {
				if (xCoordNew >= 0 && xCoordNew <= 7 && yCoordNew >= 0 && yCoordNew <= 7) {
					if (xCoordNew == xCoordOld && yCoordNew == yCoordOld) {
						return false;
					} else {
						if (xDifference == 0) {
							if (yDifference > 0) {
								for (int i = yCoordOld + 1; i <= yCoordNew; i++) {
									
									if (i < yCoordNew) {
										if (squares[xCoordOld][i] != 0) {
											return false;
										}
									} else {
										return colourOnSquare(colour, squares, xCoordOld, i);
									}
										
								}
							} else {
								for (int i = yCoordOld - 1; i >= yCoordNew; i--) {
									if (i > yCoordNew) {
										if (squares[xCoordOld][i] != 0) {
											return false;
										}
									} else {
										return colourOnSquare(colour, squares, xCoordOld, i);
									}
								}
							}
						} else if (yDifference == 0) {
							if (xDifference > 0) {
								for (int i = xCoordOld + 1; i <= xCoordNew; i++) {
									
									if (i < xCoordNew) {
										if (squares[i][yCoordOld] != 0) {
											//System.out.println("movement error");
											return false;
										}
									} else {
										return colourOnSquare(colour, squares, i, yCoordOld);
									}
										
								}
							} else {
								for (int i = xCoordOld - 1; i >= xCoordNew; i--) {
									if (i > xCoordNew) {
										if (squares[i][yCoordOld] != 0) {
											return false;
										}
									} else {
										return colourOnSquare(colour, squares, i, yCoordOld);
									}
								}
							}
						} else if (Math.abs(xDifference) == Math.abs(yDifference)) {
							if (xDifference > 0) {
								if (yDifference > 0) {
									for (int i = 1; i <= xDifference; i++) {
										if (i < xDifference) {
											if (squares[xCoordOld + i][yCoordOld + i] != 0) {
												return false;
											}
										} else {
											return colourOnSquare(colour, squares, xCoordOld + i, yCoordOld + i);
										}	
									}
								} else {
									for (int i = 1; i <= xDifference; i++) {
										if (i < xDifference) {
											if (squares[xCoordOld + i][yCoordOld - i] != 0) {
												return false;
											}
										} else {
											return colourOnSquare(colour, squares, xCoordOld + i, yCoordOld - i);
										}
										
									}
								}
							} else {
								if (yDifference < 0) {
									for (int i = -1; i >= xDifference; i--) {
										if (i > xDifference) {
											if (squares[xCoordOld + i][yCoordOld + i] != 0) {
												return false;
											}
										} else {
											return colourOnSquare(colour, squares, xCoordOld + i, yCoordOld + i);
										}
		
									}
								} else {
									for (int i = -1; i >= xDifference; i--) {
										if (i > xDifference) {
											if (squares[xCoordOld + i][yCoordOld - i] != 0) {
												return false;
											}
										} else {
											return colourOnSquare(colour, squares, xCoordOld + i, yCoordOld - i);
										}
									}
								}
							}
							return true;
						}
						return true;
					}
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
		
	}
	
	public void updateBoard(int xNew, int yNew) {
		
		xCoordNew = xNew;
		yCoordNew = yNew;
		
		gameBoard[xNew][yNew] = 1;
	}
}

class Pawn extends Piece {
	//int xCoordOld;
	//int yCoordOld;
	//int xCoordNew;
	//int yCoordNew;
	//boolean colour;
	//boolean firstMove;
	//boolean isCaptured;
	
	public Pawn(int xCoordOld, int yCoordOld, boolean firstMove, boolean colour, boolean isCheckImportant) {
		this.xCoordOld = xCoordOld;
		this.yCoordOld = yCoordOld;
		this.firstMove = firstMove;
		this.colour = colour;
		this.isCheckImportant = isCheckImportant;
	}
	
	public boolean Move(int[][] squares, boolean turn) {
		
		//System.out.println("Old Coordinates: X = " + this.xCoordOld + ", Y = " + this.yCoordOld);
		//System.out.println("New Coordinates: X = " + this.xCoordNew + ", Y = " + this.yCoordNew);
		
		if (canMove(xCoordOld, xCoordNew, yCoordOld, yCoordNew, squares, this.colour, turn, this.isCheckImportant, false)) {
			//System.out.println("general pawn true");
			if (this.colour) {
				if (xCoordNew == xCoordOld + 1 || xCoordNew == xCoordOld - 1) {
					if (yCoordNew == yCoordOld - 1) {
						if (squares[xCoordNew][yCoordNew] > 6) {
							return true;
						} else {
							return false;
						}
					} else {
						return false;
					}
				} else {
					if (squares[xCoordNew][yCoordNew] > 6) {
						return false;
					} else {
						if (firstMove) {
							if (xCoordOld == xCoordNew && yCoordNew >= yCoordOld - 2 && yCoordNew < yCoordOld) {
								return true;
							} else {
								return false;
							}
						} else {
							if (xCoordOld == xCoordNew && yCoordNew == yCoordOld - 1) {
								return true;
							} else {
								return false;
							}
						}
					}
				}
					
			} else {
				if (xCoordNew == xCoordOld + 1 || xCoordNew == xCoordOld - 1) {
					if (yCoordNew == yCoordOld + 1) {
						if (squares[xCoordNew][yCoordNew] > 0 && squares[xCoordNew][yCoordNew] < 7) {
							return true;
						} else {
							return false;
						}
					} else {
						return false;
					}
				} else {
					if (squares[xCoordNew][yCoordNew] > 0 && squares[xCoordNew][yCoordNew] < 7) {
						return false;
					} else {
						if (firstMove) {
							if (xCoordOld == xCoordNew && yCoordNew <= yCoordOld + 2 && yCoordNew > yCoordOld) {
								return true;
							} else {
								return false;
							}
						} else {
							if (xCoordOld == xCoordNew && yCoordNew == yCoordOld + 1) {
								return true;
							} else {
								return false;
							}
						}
					}
				}
			}
		} else {
			return false;
		}
	}
	
	public boolean isEndRank() {
		if (this.colour) {
			if (yCoordOld == 0) {
				return true;
			} else {
				return false;
			}
		} else {
			if (yCoordOld == 7) {
				return true;
			} else {
				return false;
			}
		}
	}
}

class Knight extends Piece {
	/*
	int xCoordOld;
	int yCoordOld;
	int xCoordNew;
	int yCoordNew;*/
	//boolean colour;
	//boolean isCaptured;
	
	public Knight(int xCoordOld, int yCoordOld, boolean colour, boolean isCheckImportant) {
		this.xCoordOld = xCoordOld;
		this.yCoordOld = yCoordOld;
		this.colour = colour;
		this.isCheckImportant = isCheckImportant;
	}
	
	public boolean Move(int[][] squares, boolean turn) {
		
		if (canMove(xCoordOld, xCoordNew, yCoordOld, yCoordNew, squares, this.colour, turn, this.isCheckImportant, false)) {
			int xDif = Math.abs(xCoordNew - xCoordOld);
			int yDif = Math.abs(yCoordNew - yCoordOld);
			
			
			if ((xDif == 2 && yDif == 1) || (xDif == 1 && yDif == 2)) {
				if (this.colour) {
					if (squares[xCoordNew][yCoordNew] > 0 && squares[xCoordNew][yCoordNew] < 7) {
						return false;
					}
					return true;
				} else {
					if (squares[xCoordNew][yCoordNew] > 6) {
						return false;
					}
					return true;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}

class Bishop extends Piece {
	/*
	int xCoordOld;
	int yCoordOld;
	int xCoordNew;
	int yCoordNew;*/
	//boolean colour;
	//boolean isCaptured;
	
	public Bishop(int xCoordOld, int yCoordOld, boolean colour, boolean isCheckImportant) {
		this.xCoordOld = xCoordOld;
		this.yCoordOld = yCoordOld;
		this.colour = colour;
		this.isCheckImportant = isCheckImportant;
	}
	
	public boolean Move(int[][] squares, boolean turn) {
		if (canMove(xCoordOld, xCoordNew, yCoordOld, yCoordNew, squares, this.colour, turn, this.isCheckImportant, false)) {
			if (Diagonals()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}

class Rook extends Piece {
	/*
	int xCoordOld;
	int yCoordOld;
	int xCoordNew;
	int yCoordNew;*/
	//boolean colour;
	//boolean isCaptured;
	
	public Rook(int xCoordOld, int yCoordOld, boolean colour, boolean isCheckImportant, boolean firstMove) {
		this.xCoordOld = xCoordOld;
		this.yCoordOld = yCoordOld;
		this.colour = colour;
		this.isCheckImportant = isCheckImportant;
		this.firstMove = firstMove;
	}
	
	public boolean Move(int[][] squares, boolean turn) {
		if (canMove(xCoordOld, xCoordNew, yCoordOld, yCoordNew, squares, this.colour, turn, this.isCheckImportant, false)) {
			if (Lines()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}

class Queen extends Piece {
	/*
	int xCoordOld;
	int yCoordOld;
	int xCoordNew;
	int yCoordNew;*/
	//boolean colour;
	//boolean isCaptured;
	
	public Queen(int xCoordOld, int yCoordOld, boolean colour, boolean isCheckImportant) {
		this.xCoordOld = xCoordOld;
		this.yCoordOld = yCoordOld;
		this.colour = colour;
		this.isCheckImportant = isCheckImportant;
	}
	
	public boolean Move(int[][] squares, boolean turn) {
		if (canMove(xCoordOld, xCoordNew, yCoordOld, yCoordNew, squares, this.colour, turn, this.isCheckImportant, false)) {
			if (Lines() || Diagonals()) {
				return true;
			} else {
				//System.out.println("queen movement false");
				return false;
			}
		} else {
			//System.out.println("general piece false");
			return false;
		}
	}
}

class King extends Piece {
	/*
	int xCoordOld;
	int yCoordOld;
	int xCoordNew;
	int yCoordNew;*/
	//boolean colour;
	//boolean isCaptured;
	//boolean isKing;
	
	public King(int xCoordOld, int yCoordOld, boolean colour, boolean isCheckImportant, boolean firstMove) {
		this.xCoordOld = xCoordOld;
		this.yCoordOld = yCoordOld;
		this.colour = colour;
		this.isCheckImportant = isCheckImportant;
		this.firstMove = firstMove;
		this.isKing = true;
	}
	
	public boolean Move(int[][] squares, boolean turn, boolean isRookFirstMove) {
		int xDif = Math.abs(xCoordNew - xCoordOld);
		int yDif = Math.abs(yCoordNew - yCoordOld);
		
		if (canMove(xCoordOld, xCoordNew, yCoordOld, yCoordNew, squares, this.colour, turn, this.isCheckImportant, isKing)) {
			boolean retVal = false;
			
			if (Lines() || Diagonals()) {	
		
				if (xDif == 1 || yDif == 1) {
					retVal = true;
					
				} else {
					retVal = false;
				}
			}
			
			if (xDif == 2 && yDif == 0) {
				System.out.println("Castle is allowed");
				retVal = canCastle(squares, isRookFirstMove);
			}
			
			return retVal;
		} else {
			return false;
		}
	}
	
	public int targetRookCastle(int oldX, int newX) {
		int xMove = newX - oldX;
		int rookX = 0;
		int rookY = 0;
		int retVal = 0;
		//1 = 0,0
		//2 = 7,0
		//3 = 0,7
		//4 = 7,7
		
		if (xMove == 2) {
			rookX = 7;
			retVal += 2;
			
			if (this.colour) {
				rookY = 7;
				retVal += 2;
			} else {
				rookY = 0;
				retVal += 0;
			}
			
		} else if (xMove == -2) {
			rookX = 0;
			retVal += 1;
			
			if (this.colour) {
				rookY = 7;
				retVal += 2;
			} else {
				rookY = 0;
				retVal += 0;
			}
		}
		
		return retVal;
	}
	
	public boolean canCastleSafely(int[][] board) {
		int increment = 0;
		int iterations = 0;
		int xMove = this.xCoordNew - this.xCoordOld;
		
		if (xMove > 0) {
			increment = 1;
		} else {
			increment = -1;
		}
		
		int limit = this.xCoordOld + 2;
		
		for (int i = this.xCoordOld; iterations <= 2; i += increment, iterations++) {
			int select = 0;
			int[][] testBoard = new int[8][8];
			
			for (int y = 0; y < 8; y++) {
				for (int x = 0; x < 8; x++) {
					testBoard[x][y] = board[x][y];
				}
			}
			
			for (int y = 0; y < 8; y++) {
				for (int x = 0; x < 8; x++) {
					if (this.colour) {
						if (testBoard[x][y] > 6) {
							select = board[x][y];
						} else {
							select = 0;
						}
					} else {
						if (testBoard[x][y] < 7 && testBoard[x][y] != 0) {
							select = board[x][y];
						} else {
							select = 0;
						}
					}
					
					switch (select) {
						case 1:
						case 7:
							Pawn myPawn = new Pawn(x, y, false, !this.colour, false);
	
							myPawn.xCoordNew = i;
							myPawn.yCoordNew = this.yCoordOld;
									
							if (myPawn.Move(board, !this.colour)) {
								System.out.println("Pawn attack!");
								return false;
							}
							
							break;
						case 2:
						case 8:
							Knight myKnight = new Knight(x, y, !this.colour, false);
	
							myKnight.xCoordNew = i;
							myKnight.yCoordNew = this.yCoordOld;
									
							if (myKnight.Move(board, !this.colour)) {
								return false;
							}
							
							break;
						case 3:
						case 9:
							Bishop myBishop = new Bishop(x, y, !this.colour, false);
	
							myBishop.xCoordNew = i;
							myBishop.yCoordNew = this.yCoordOld;
									
							if (myBishop.Move(board, !this.colour)) {
								return false;
							}
							
							break;
						case 4:
						case 10:
							Rook myRook = new Rook(x, y, !this.colour, false, true);
	
							myRook.xCoordNew = i;
							myRook.yCoordNew = this.yCoordOld;
									
							if (myRook.Move(board, !this.colour)) {
								return false;
							}
							
							break;
						case 5:
						case 11:
							Queen myQueen = new Queen(x, y, !this.colour, false);
	
							myQueen.xCoordNew = i;
							myQueen.yCoordNew = this.yCoordOld;
									
							if (myQueen.Move(board, !this.colour)) {
								return false;
							}
							
							break;
						case 6:
						case 12:
							King myKing = new King(x, y, !this.colour, false, true);
	
							myKing.xCoordNew = i;
							myKing.yCoordNew = this.yCoordOld;
									
							if (myKing.Move(board, !this.colour, false)) {
								return false;
							}
							
							break;
						default:
							break;
					}
				}
			}
			/*
			if (i != this.xCoordOld) {
				if (board[i][this.yCoordOld] > 0) {
					return false;
				}
			}*/
		}
		
		return true;
	}
	
	public boolean canCastle(int[][] logicBoard, boolean isRookFirstMove) {
		if (this.firstMove) {
			//System.out.println("King has not moved yet");
			if (isRookFirstMove) {
				//System.out.println("Rook has not moved yet");
				if (canCastleSafely(logicBoard)) {
					System.out.println("Safety");
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
			
		} else {
			return false;
		}
	}
}

class logicContainer {
	Pawn[] pawns;
	Knight[] knights;
	Bishop[] bishops;
	Rook[] rooks;
	Queen[] queens;
	King[] kings;
	
	public logicContainer() {
		this.pawns = new Pawn[16];
		this.knights = new Knight[4];
		this.bishops = new Bishop[4];
		this.rooks = new Rook[4];
		this.queens = new Queen[2];
		this.kings = new King[2];
		
		
	}
	
}

class selectPiece {
	double mouseX;
	double mouseY;
	int xSelect;
	int ySelect;
	boolean mouseDown;
	boolean rightTurn;
	
	public selectPiece(boolean mouseDown) {
		this.mouseDown = mouseDown;
	}
}

class checkHandler {
	boolean isCheck;
	int kingX;
	int kingY;
	
	public boolean isPieceChecking(int piece, int xCoordOld, int yCoordOld, int[][] logicBoard, boolean turn) {
		boolean retVal = false;
		
		switch (piece) {
			case 1:
			case 7:
				Pawn myPawn = new Pawn(xCoordOld, yCoordOld, true, !turn, false);
				
				for (int i = 0; i < 8; i++) {
					for (int j = 0; j < 8; j++) {
						
						myPawn.xCoordNew = j;
						myPawn.yCoordNew = i;
						
						if (myPawn.Move(logicBoard, !turn)) {
							
							if (turn) {
								if (logicBoard[j][i] == 6) {
									retVal = true;
									
									this.kingX = j;
									this.kingY = i;
								}
							} else {
								if (logicBoard[j][i] == 12) {
									retVal = true;
									
									this.kingX = j;
									this.kingY = i;
								}
							}
							
						}
					}
				}
				
				break;
			case 2:
			case 8:
				Knight myKnight = new Knight(xCoordOld, yCoordOld, !turn, false);
				
				for (int i = 0; i < 8; i++) {
					for (int j = 0; j < 8; j++) {
						
						myKnight.xCoordNew = j;
						myKnight.yCoordNew = i;
						
						if (myKnight.Move(logicBoard, !turn)) {
							
							if (turn) {
								if (logicBoard[j][i] == 6) {
									this.kingX = j;
									this.kingY = i;
									
									retVal = true;
								}
							} else {
								if (logicBoard[j][i] == 12) {
									retVal = true;
									
									this.kingX = j;
									this.kingY = i;
								}
							}
							
						}
					}
				}
				
				break;
			case 3:
			case 9:
				Bishop myBishop = new Bishop(xCoordOld, yCoordOld, !turn, false);
				
				for (int i = 0; i < 8; i++) {
					for (int j = 0; j < 8; j++) {
						
						myBishop.xCoordNew = j;
						myBishop.yCoordNew = i;
						
						
						if (myBishop.Move(logicBoard, !turn)) {
							
							if (turn) {
								if (logicBoard[j][i] == 6) {
									retVal = true;
									
									this.kingX = j;
									this.kingY = i;
								}
							} else {
								if (logicBoard[j][i] == 12) {
									//System.out.println("Bishop HIT The King");
									
									retVal = true;
									
									this.kingX = j;
									this.kingY = i;
								}
							}
							
						}
					}
				}
				
				break;
			case 4:
			case 10:
				Rook myRook = new Rook(xCoordOld, yCoordOld, !turn, false, true);
				
				for (int i = 0; i < 8; i++) {
					for (int j = 0; j < 8; j++) {
						
						myRook.xCoordNew = j;
						myRook.yCoordNew = i;
						
						if (myRook.Move(logicBoard, !turn)) {
							
							if (turn) {
								if (logicBoard[j][i] == 6) {
									retVal = true;
									
									this.kingX = j;
									this.kingY = i;
								}
							} else {
								if (logicBoard[j][i] == 12) {
									retVal = true;
									
									this.kingX = j;
									this.kingY = i;
								}
							}
							
						}
					}
				}
				
				break;
			case 5:
			case 11:
				Queen myQueen = new Queen(xCoordOld, yCoordOld, !turn, false);
				
				for (int i = 0; i < 8; i++) {
					for (int j = 0; j < 8; j++) {
						
						myQueen.xCoordNew = j;
						myQueen.yCoordNew = i;
						
						if (myQueen.Move(logicBoard, !turn)) {
							
							if (turn) {
								if (logicBoard[j][i] == 6) {
									retVal = true;
									
									this.kingX = j;
									this.kingY = i;
								}
							} else {
								if (logicBoard[j][i] == 12) {
									retVal = true;
									
									this.kingX = j;
									this.kingY = i;
								}
							}
							
						}
					}
				}
				
				break;
			case 6:
			case 12:
				King myKing = new King(xCoordOld, yCoordOld, !turn, false, true);
				
				for (int i = 0; i < 8; i++) {
					for (int j = 0; j < 8; j++) {
						
						myKing.xCoordNew = j;
						myKing.yCoordNew = i;
						
						if (myKing.Move(logicBoard, !turn, false)) {
							
							if (turn) {
								if (logicBoard[j][i] == 6) {
									retVal = true;
									
									this.kingX = j;
									this.kingY = i;
								}
							} else {
								if (logicBoard[j][i] == 12) {
									retVal = true;
									
									this.kingX = j;
									this.kingY = i;
								}
							}
							
						}
					}
				}
				
				break;
			default:
				retVal = false;
			
				break;
		}
		
		return retVal;
	}
	
	public boolean isKingInCheck(boolean turn, int[][] logicBoard) {
		boolean retVal = true;
		//System.out.println("isKingInCheck method called");
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				int pieceCheck = logicBoard[x][y];
				
				if (pieceCheck == 3 || pieceCheck == 9) {
					//System.out.println("Piece selected is " + pieceCheck);
				}
				
				//System.out.println("X: " + x + " Y: " + y);
				
				if (turn) {
					if (pieceCheck > 6) {
						retVal = isPieceChecking(pieceCheck, x, y, logicBoard, turn);
						
						if (retVal) {
							return true;
						}
					}
				} else {
					if (pieceCheck != 0 && pieceCheck < 7) {
						retVal = isPieceChecking(pieceCheck, x, y, logicBoard, turn);
						
						if (retVal) {
							return true;
						}
					}
				}
			}
		}
		
		return false;
	}
	
	public boolean isPreventedByCheck(boolean turn, int[][] logicBoard, boolean isKing) {
		boolean retVal = true;
		
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				int pieceCheck = logicBoard[x][y];
				
				if (turn) {
					if (isKing) {
						if (pieceCheck > 6) {
							retVal = isPieceChecking(pieceCheck, x, y, logicBoard, turn);
							
							if (retVal) {
								return true;
							}
						}
						
					} else {
						if (pieceCheck > 6 && pieceCheck < 12) {
							retVal = isPieceChecking(pieceCheck, x, y, logicBoard, turn);
							
							if (retVal) {
								return true;
							}
						}
					}
					
				} else {
					if (isKing) {
						if (pieceCheck < 7) {
							retVal = isPieceChecking(pieceCheck, x, y, logicBoard, turn);
							
							if (retVal) {
								return true;
							}
						}
						
					} else {
						if (pieceCheck < 6) {
							retVal = isPieceChecking(pieceCheck, x, y, logicBoard, turn);
							
							if (retVal) {
								return true;
							}
						}
					}
				}
			}
		}
		
		return false;
	}
}

class rookVector {
	int x;
	int y;
}