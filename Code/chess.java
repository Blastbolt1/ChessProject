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
		logicBoard.updateMoveHistory(Game.moveNum);
		
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
		Game.initPromo(true);
		
		selectPiece selector = new selectPiece(false);
		
		rookVector rookVector = new rookVector();
		
		myScene.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				if (Game.promoBool) {
					selector.mouseX = event.getX();
					selector.mouseY = event.getY();
					
					double targetX = Math.floor((selector.mouseX - xOffset)/squareSize);
					double targetY = Math.floor((selector.mouseY - yOffset)/squareSize);
					
					selector.xSelect = (int)targetX;
					selector.ySelect = (int)targetY;
	
					int x = selector.xSelect;
					int y = selector.ySelect;
					
					//System.out.println("This Piece can move to the following squares:");
					
					if (x > -1 && x < 8 && y > -1 && y < 8) {
						Game.pane.getChildren().remove(Game.pieceGraphicsMaster[x][y]);
						Game.pane.getChildren().add(Game.pieceGraphicsMaster[x][y]);
						
						switch (logicBoard.logicBoard[x][y]) {
							case 1:
							case 7:
								Pawn myPawn = new Pawn(x, y, Game.arrayLogic[x][y].firstMove, Game.arrayLogic[x][y].colour, true);
								Game.imageXOffset = 0.092;
								Game.imageYOffset = 0.07;
								
								for (int i = 0; i < 8; i++) {
									for (int j = 0; j < 8; j++) {
										
										myPawn.xCoordNew = j;
										myPawn.yCoordNew = i;
										
										if (myPawn.Move(logicBoard.logicBoard, Game.turn, logicBoard.moveHistory, Game.moveNum)) {
											Game.possibilityBoard[j][i] = true;
											Game.pane.getChildren().add(Game.squareSelect[j][i]);
											//System.out.println("X: " + j + " Y: " + i);
										} else {
											Game.possibilityBoard[j][i] = false;
										}
									}
								}
								
								//Game.printBoard(logicBoard.logicBoard);
								
								break;
							case 2:
							case 8:
								Knight myKnight = new Knight(x, y, Game.arrayLogic[x][y].colour, true);
								Game.imageXOffset = 0.082;
								Game.imageYOffset = 0.02;
		
								for (int i = 0; i < 8; i++) {
									for (int j = 0; j < 8; j++) {
										
										myKnight.xCoordNew = j;
										myKnight.yCoordNew = i;
										
										if (myKnight.Move(logicBoard.logicBoard, Game.turn, logicBoard.moveHistory, Game.moveNum)) {
											Game.possibilityBoard[j][i] = true;
											Game.pane.getChildren().add(Game.squareSelect[j][i]);
											//System.out.println("X: " + j + " Y: " + i);
										} else {
											Game.possibilityBoard[j][i] = false;
										}
									}
								}
								
								break;
							case 3:
							case 9:
								Bishop myBishop = new Bishop(x, y, Game.arrayLogic[x][y].colour, true);
								Game.imageXOffset = 0.082;
								Game.imageYOffset = 0.02;
		
								for (int i = 0; i < 8; i++) {
									for (int j = 0; j < 8; j++) {
										
										myBishop.xCoordNew = j;
										myBishop.yCoordNew = i;
										
										if (myBishop.Move(logicBoard.logicBoard, Game.turn, logicBoard.moveHistory, Game.moveNum)) {
											Game.possibilityBoard[j][i] = true;
											Game.pane.getChildren().add(Game.squareSelect[j][i]);
											//System.out.println("X: " + j + " Y: " + i);
										} else {
											Game.possibilityBoard[j][i] = false;
										}
									}
								}
								
								break;
							case 4:
							case 10:
								Rook myRook = new Rook(x, y, Game.arrayLogic[x][y].colour, true, Game.arrayLogic[x][y].firstMove);
								Game.imageXOffset = 0.1;
								Game.imageYOffset = 0.06;
								
								for (int i = 0; i < 8; i++) {
									for (int j = 0; j < 8; j++) {
										
										myRook.xCoordNew = j;
										myRook.yCoordNew = i;
										
										if (myRook.Move(logicBoard.logicBoard, Game.turn, logicBoard.moveHistory, Game.moveNum)) {
											Game.possibilityBoard[j][i] = true;
											Game.pane.getChildren().add(Game.squareSelect[j][i]);
											//System.out.println("X: " + j + " Y: " + i);
										} else {
											Game.possibilityBoard[j][i] = false;
										}
									}
								}
								
								break;
							case 5:
							case 11:
								Queen myQueen = new Queen(x, y, Game.arrayLogic[x][y].colour, true);
								Game.imageXOffset = 0.092;
								Game.imageYOffset = 0.03;
								
								for (int i = 0; i < 8; i++) {
									for (int j = 0; j < 8; j++) {
										
										myQueen.xCoordNew = j;
										myQueen.yCoordNew = i;
										
										if (myQueen.Move(logicBoard.logicBoard, Game.turn, logicBoard.moveHistory, Game.moveNum)) {
											Game.possibilityBoard[j][i] = true;
											Game.pane.getChildren().add(Game.squareSelect[j][i]);
											//System.out.println("X: " + j + " Y: " + i);
										} else {
											Game.possibilityBoard[j][i] = false;
										}
									}
								}						
								
								break;
							case 6:
							case 12:
								King myKing = new King(x, y, Game.arrayLogic[x][y].colour, true, Game.arrayLogic[x][y].firstMove);
								Game.imageXOffset = 0.092;
								Game.imageYOffset = 0.03;
								
								for (int i = 0; i < 8; i++) {
									for (int j = 0; j < 8; j++) {
										
										myKing.xCoordNew = j;
										myKing.yCoordNew = i;
										
										int[] coords = new int[2];
										coords = Game.getRookCoords(myKing.targetRookCastle(myKing.xCoordOld, myKing.xCoordNew));
										//rookVector.x = coords[0];
										//rookVector.y = coords[1];
										
										//System.out.println("Rook X Selected: " + rookVector.x);
										//System.out.println("Rook Y Selected: " + rookVector.y);
										
										if (myKing.Move(logicBoard.logicBoard, Game.turn, Game.isRookFirstMove(coords[0], coords[1]), logicBoard.moveHistory, Game.moveNum)) {
											Game.possibilityBoard[j][i] = true;
											Game.pane.getChildren().add(Game.squareSelect[j][i]);
											//Game.arrayLogic[x][y].firstMove = false;
											//System.out.println("X: " + j + " Y: " + i);
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
			}
		});
		
		myScene.setOnMouseReleased(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				if (Game.promoBool) {
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
							
							//System.out.println("X New is " + Game.arrayLogic[x][y].xCoordNew);
							//System.out.println("Y New is " + Game.arrayLogic[x][y].yCoordNew);
							
							int xDif = Math.abs(Game.arrayLogic[x][y].xCoordNew - Game.arrayLogic[x][y].xCoordOld);
							int rookX = 0;
							int rookY = 0;
							boolean isCastling = false;
							King myKing = new King(x, y, Game.arrayLogic[x][y].colour, true, Game.arrayLogic[x][y].firstMove);
							int[] coords = new int[2];
							
							if (logicBoard.logicBoard[x][y] % 6 == 0 && xDif == 2) {
								isCastling = true;
								
								myKing.xCoordNew = xint;
								myKing.yCoordNew = yint;
								
								coords = Game.getRookCoords(myKing.targetRookCastle(myKing.xCoordOld, myKing.xCoordNew));
								rookVector.x = coords[0];
								rookVector.y = coords[1];
							}
							
							boolean canMove = true;
							
							int checkX = 0;
							int checkY = 0;
							
							if (xint > -1 && xint < 8 && yint > -1 && yint < 8) {
								if (Game.possibilityBoard[xint][yint] == false) {
									canMove = false;
								}
							} else {
								canMove = false;
							}
							
							for (int i = 0; i < 8; i++) {
								for (int j = 0; j < 8; j++) {
									Game.pane.getChildren().remove(Game.squareSelect[j][i]);
								}
							}
							
							Game.movePiece(canMove, Game.pieceGraphicsMaster[x][y], Game.arrayLogic[x][y], logicBoard.logicBoard, xint, yint, logicBoard.logicBoard[x][y], Game.imageXOffset, Game.imageYOffset, isCastling, rookVector.x, rookVector.y);
							
							if (canMove) {
								
								Game.pane.getChildren().remove(Game.visualCheck);
								Game.turn = !Game.turn;
								Game.moveNum++;
								logicBoard.updateMoveHistory(Game.moveNum);
								checkHandler checkHandler = new checkHandler();
								//boolean checkDetect = checkHandler.isKingInCheck(Game.turn, logicBoard.logicBoard);
								
								if (checkHandler.isKingInCheck(Game.turn, logicBoard.logicBoard, logicBoard.moveHistory, Game.moveNum)) {
									Game.imagePos(Game.visualCheck, checkHandler.kingX, checkHandler.kingY, 0.0005 * squareSize, 0.0005 * squareSize, 1.1 * squareSize, 1.1 * squareSize);
									Game.pane.getChildren().remove(Game.pieceGraphicsMaster[checkHandler.kingX][checkHandler.kingY]);
									Game.pane.getChildren().add(Game.visualCheck);
									Game.pane.getChildren().add(Game.pieceGraphicsMaster[checkHandler.kingX][checkHandler.kingY]);
									//System.out.println("Ayo you in check");
									Game.isCheck = false;
								}
							}	
						}
					}
				}
				
			}
		});
		
		myScene.setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				if (Game.promoBool) {
					selector.mouseX = event.getX();
					selector.mouseY = event.getY();
					
					int x = selector.xSelect;
					int y = selector.ySelect;
					
					//Game.printBoard(logicBoard.logicBoard);
					
					if (x > -1 && x < 8 && y > -1 && y < 8) {
						if (logicBoard.logicBoard[selector.xSelect][selector.ySelect] > 0) {
							if (Game.turn == Game.arrayLogic[selector.xSelect][selector.ySelect].colour) {
								Game.pieceGraphicsMaster[selector.xSelect][selector.ySelect].setX(selector.mouseX - (squareSize/2));
								Game.pieceGraphicsMaster[selector.xSelect][selector.ySelect].setY(selector.mouseY - (squareSize/2));
							}
						}
					}
				}
			}
		});
		/*
		for (int i = 0; i < 4; i++) {
			System.out.println("a");
			int j = i;
			
			Game.promoPieces[j].setOnMouseReleased(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent event) {
					System.out.println(Game.turn);
					switch (j) {
						case 0:
							
							if (Game.turn) {
								logicBoard.logicBoard[Game.promoX][Game.promoY] = 8;
								Game.arrayLogic[Game.promoX][Game.promoY] = new Knight(Game.promoX, Game.promoY, false, false);
								Game.pieceGraphicsMaster[Game.promoX][Game.promoY] = new ImageView(Game.BlackKnight);
							} else {
								logicBoard.logicBoard[Game.promoX][Game.promoY] = 2;
								Game.arrayLogic[Game.promoX][Game.promoY] = new Knight(Game.promoX, Game.promoY, true, false);
								Game.pieceGraphicsMaster[Game.promoX][Game.promoY] = new ImageView(Game.WhiteKnight);
							}
							
							break;
						case 1:
							if (Game.turn) {
								logicBoard.logicBoard[Game.promoX][Game.promoY] = 9;
								Game.arrayLogic[Game.promoX][Game.promoY] = new Bishop(Game.promoX, Game.promoY, false, false);
								Game.pieceGraphicsMaster[Game.promoX][Game.promoY] = new ImageView(Game.BlackBishop);
							} else {
								logicBoard.logicBoard[Game.promoX][Game.promoY] = 3;
								Game.arrayLogic[Game.promoX][Game.promoY] = new Bishop(Game.promoX, Game.promoY, true, false);
								Game.pieceGraphicsMaster[Game.promoX][Game.promoY] = new ImageView(Game.WhiteBishop);
							}
							
							break;
						case 2:
							if (Game.turn) {
								logicBoard.logicBoard[Game.promoX][Game.promoY] = 10;
								Game.arrayLogic[Game.promoX][Game.promoY] = new Rook(Game.promoX, Game.promoY, false, false, false);
								Game.pieceGraphicsMaster[Game.promoX][Game.promoY] = new ImageView(Game.BlackRook);
							} else {
								logicBoard.logicBoard[Game.promoX][Game.promoY] = 4;
								Game.arrayLogic[Game.promoX][Game.promoY] = new Rook(Game.promoX, Game.promoY, true, false, false);
								Game.pieceGraphicsMaster[Game.promoX][Game.promoY] = new ImageView(Game.WhiteRook);
							}
							
							break;
						case 3:	
							if (Game.turn) {
								logicBoard.logicBoard[Game.promoX][Game.promoY] = 11;
								Game.arrayLogic[Game.promoX][Game.promoY] = new Queen(Game.promoX, Game.promoY, false, false);
								Game.pieceGraphicsMaster[Game.promoX][Game.promoY] = new ImageView(Game.BlackQueen);
							} else {
								logicBoard.logicBoard[Game.promoX][Game.promoY] = 5;
								Game.arrayLogic[Game.promoX][Game.promoY] = new Queen(Game.promoX, Game.promoY, true, false);
								Game.pieceGraphicsMaster[Game.promoX][Game.promoY] = new ImageView(Game.WhiteQueen);
							}

							break;
					}
					
					Game.imagePos(Game.pieceGraphicsMaster[Game.promoX][Game.promoY], Game.promoX, Game.promoY, 0.082, 0.02, squareSize, 1.185 * squareSize);
					Game.pane.getChildren().add(Game.pieceGraphicsMaster[Game.promoX][Game.promoY]);
					
					for (int k = 0; k < 4; k++) {
						Game.pane.getChildren().remove(Game.promoPieces[k]);
						Game.pane.getChildren().remove(Game.glow[k]);
					}
					Game.pane.getChildren().remove(Game.promotionText);
					Game.pane.getChildren().remove(Game.shader);
				}
			});
		}*/
		
		myStage.show();
	}
}