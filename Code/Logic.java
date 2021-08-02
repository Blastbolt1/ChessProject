import java.lang.Math;
import java.util.Arrays;


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
				checkRetVal = checkHandler.isPinned(turn, testBoard, isKing);
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
	boolean colour;
	boolean isCaptured;
	
	public Rook(int xCoordOld, int yCoordOld, boolean colour, boolean isCheckImportant) {
		this.xCoordOld = xCoordOld;
		this.yCoordOld = yCoordOld;
		this.colour = colour;
		this.isCheckImportant = isCheckImportant;
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
	boolean isKing;
	
	public King(int xCoordOld, int yCoordOld, boolean colour, boolean isCheckImportant) {
		this.xCoordOld = xCoordOld;
		this.yCoordOld = yCoordOld;
		this.colour = colour;
		this.isCheckImportant = isCheckImportant;
		this.isKing = true;
	}
	
	public boolean Move(int[][] squares, boolean turn) {
		if (canMove(xCoordOld, xCoordNew, yCoordOld, yCoordNew, squares, this.colour, turn, this.isCheckImportant, isKing)) {
			if (Lines() || Diagonals()) {
				int xDif = Math.abs(xCoordNew - xCoordOld);
				int yDif = Math.abs(yCoordNew - yCoordOld);
		
				if (xDif == 1 || yDif == 1) {
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
									System.out.println("Bishop HIT The King");
									
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
				Rook myRook = new Rook(xCoordOld, yCoordOld, !turn, false);
				
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
				King myKing = new King(xCoordOld, yCoordOld, !turn, false);
				
				for (int i = 0; i < 8; i++) {
					for (int j = 0; j < 8; j++) {
						
						myKing.xCoordNew = j;
						myKing.yCoordNew = i;
						
						if (myKing.Move(logicBoard, !turn)) {
							
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
		System.out.println("isKingInCheck method called");
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				int pieceCheck = logicBoard[x][y];
				
				if (pieceCheck == 3 || pieceCheck == 9) {
					System.out.println("Piece selected is " + pieceCheck);
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
	
	public boolean isPinned(boolean turn, int[][] logicBoard, boolean isKing) {
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