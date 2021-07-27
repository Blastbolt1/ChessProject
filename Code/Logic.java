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
	
	public boolean canMove(int xOld, int xNew, int yOld, int yNew, int[][] squares, boolean colour, boolean turn, boolean isCheck) {
		
		xCoordOld = xOld;
		xCoordNew = xNew;
		yCoordOld = yOld;
		yCoordNew = yNew;
		
		int xDifference = xCoordNew - xCoordOld;
		int yDifference = yCoordNew - yCoordOld;
		
		if (colour == turn) {
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
	
	public Pawn(int xCoordOld, int yCoordOld, boolean firstMove, boolean colour, boolean isCaptured) {
		this.xCoordOld = xCoordOld;
		this.yCoordOld = yCoordOld;
		this.firstMove = firstMove;
		this.colour = colour;
		this.isCaptured = isCaptured;
	}
	
	public boolean Move(int[][] squares, boolean turn) {
		
		//System.out.println("Old Coordinates: X = " + this.xCoordOld + ", Y = " + this.yCoordOld);
		//System.out.println("New Coordinates: X = " + this.xCoordNew + ", Y = " + this.yCoordNew);
		
		if (canMove(xCoordOld, xCoordNew, yCoordOld, yCoordNew, squares, this.colour, turn)) {
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
	
	public Knight(int xCoordOld, int yCoordOld, boolean colour, boolean isCaptured) {
		this.xCoordOld = xCoordOld;
		this.yCoordOld = yCoordOld;
		this.colour = colour;
		this.isCaptured = isCaptured;
	}
	
	public boolean Move(int[][] squares, boolean turn) {
		
		if (canMove(xCoordOld, xCoordNew, yCoordOld, yCoordNew, squares, this.colour, turn)) {
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
	
	public Bishop(int xCoordOld, int yCoordOld, boolean colour, boolean isCaptured) {
		this.xCoordOld = xCoordOld;
		this.yCoordOld = yCoordOld;
		this.colour = colour;
		this.isCaptured = isCaptured;
	}
	
	public boolean Move(int[][] squares, boolean turn) {
		if (canMove(xCoordOld, xCoordNew, yCoordOld, yCoordNew, squares, this.colour, turn)) {
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
	
	public Rook(int xCoordOld, int yCoordOld, boolean colour, boolean isCaptured) {
		this.xCoordOld = xCoordOld;
		this.yCoordOld = yCoordOld;
		this.colour = colour;
		this.isCaptured = isCaptured;
	}
	
	public boolean Move(int[][] squares, boolean turn) {
		if (canMove(xCoordOld, xCoordNew, yCoordOld, yCoordNew, squares, this.colour, turn)) {
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
	
	public Queen(int xCoordOld, int yCoordOld, boolean colour, boolean isCaptured) {
		this.xCoordOld = xCoordOld;
		this.yCoordOld = yCoordOld;
		this.colour = colour;
		this.isCaptured = isCaptured;
	}
	
	public boolean Move(int[][] squares, boolean turn) {
		if (canMove(xCoordOld, xCoordNew, yCoordOld, yCoordNew, squares, this.colour, turn)) {
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
	
	public King(int xCoordOld, int yCoordOld, boolean colour, boolean isCaptured) {
		this.xCoordOld = xCoordOld;
		this.yCoordOld = yCoordOld;
		this.colour = colour;
		this.isCaptured = isCaptured;
	}
	
	public boolean Move(int[][] squares, boolean turn) {
		if (canMove(xCoordOld, xCoordNew, yCoordOld, yCoordNew, squares, this.colour, turn)) {
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