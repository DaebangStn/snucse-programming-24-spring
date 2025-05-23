// 2017-12147, 기계공학부, 임건호

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.*;
import javax.swing.border.*;
//======================================================Don't modify below===============================================================//
enum PieceType {king, queen, bishop, knight, rook, pawn, none}
enum PlayerColor {black, white, none}
	
public class ChessBoard {
	private final JPanel gui = new JPanel(new BorderLayout(3, 3));
	private JPanel chessBoard;
	private JButton[][] chessBoardSquares = new JButton[8][8];
	private Piece[][] chessBoardStatus = new Piece[8][8];
	private ImageIcon[] pieceImage_b = new ImageIcon[7];
	private ImageIcon[] pieceImage_w = new ImageIcon[7];
	private JLabel message = new JLabel("Enter Reset to Start");

	ChessBoard(){
		initPieceImages();
		initBoardStatus();
		initializeGui();
	}
	
	public final void initBoardStatus(){
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++) chessBoardStatus[j][i] = new Piece();
		}
	}
	
	public final void initPieceImages(){
		pieceImage_b[0] = new ImageIcon(new ImageIcon("./img/king_b.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
		pieceImage_b[1] = new ImageIcon(new ImageIcon("./img/queen_b.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
		pieceImage_b[2] = new ImageIcon(new ImageIcon("./img/bishop_b.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
		pieceImage_b[3] = new ImageIcon(new ImageIcon("./img/knight_b.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
		pieceImage_b[4] = new ImageIcon(new ImageIcon("./img/rook_b.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
		pieceImage_b[5] = new ImageIcon(new ImageIcon("./img/pawn_b.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
		pieceImage_b[6] = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
		
		pieceImage_w[0] = new ImageIcon(new ImageIcon("./img/king_w.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
		pieceImage_w[1] = new ImageIcon(new ImageIcon("./img/queen_w.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
		pieceImage_w[2] = new ImageIcon(new ImageIcon("./img/bishop_w.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
		pieceImage_w[3] = new ImageIcon(new ImageIcon("./img/knight_w.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
		pieceImage_w[4] = new ImageIcon(new ImageIcon("./img/rook_w.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
		pieceImage_w[5] = new ImageIcon(new ImageIcon("./img/pawn_w.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
		pieceImage_w[6] = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
	}
	
	public ImageIcon getImageIcon(Piece piece){
		if(piece.color.equals(PlayerColor.black)){
			if(piece.type.equals(PieceType.king)) return pieceImage_b[0];
			else if(piece.type.equals(PieceType.queen)) return pieceImage_b[1];
			else if(piece.type.equals(PieceType.bishop)) return pieceImage_b[2];
			else if(piece.type.equals(PieceType.knight)) return pieceImage_b[3];
			else if(piece.type.equals(PieceType.rook)) return pieceImage_b[4];
			else if(piece.type.equals(PieceType.pawn)) return pieceImage_b[5];
			else return pieceImage_b[6];
		}
		else if(piece.color.equals(PlayerColor.white)){
			if(piece.type.equals(PieceType.king)) return pieceImage_w[0];
			else if(piece.type.equals(PieceType.queen)) return pieceImage_w[1];
			else if(piece.type.equals(PieceType.bishop)) return pieceImage_w[2];
			else if(piece.type.equals(PieceType.knight)) return pieceImage_w[3];
			else if(piece.type.equals(PieceType.rook)) return pieceImage_w[4];
			else if(piece.type.equals(PieceType.pawn)) return pieceImage_w[5];
			else return pieceImage_w[6];
		}
		else return pieceImage_w[6];
	}

	public final void initializeGui(){
		gui.setBorder(new EmptyBorder(5, 5, 5, 5));
	    JToolBar tools = new JToolBar();
	    tools.setFloatable(false);
	    gui.add(tools, BorderLayout.PAGE_START);
	    JButton startButton = new JButton("Reset");
	    startButton.addActionListener(new ActionListener(){
	    	public void actionPerformed(ActionEvent e){
	    		initiateBoard();
	    	}
	    });
	    
	    tools.add(startButton);
	    tools.addSeparator();
	    tools.add(message);

	    chessBoard = new JPanel(new GridLayout(0, 8));
	    chessBoard.setBorder(new LineBorder(Color.BLACK));
	    gui.add(chessBoard);
	    ImageIcon defaultIcon = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
	    Insets buttonMargin = new Insets(0,0,0,0);
	    for(int i=0; i<chessBoardSquares.length; i++) {
	        for (int j = 0; j < chessBoardSquares[i].length; j++) {
	        	JButton b = new JButton();
	        	b.addActionListener(new ButtonListener(i, j));
	            b.setMargin(buttonMargin);
	            b.setIcon(defaultIcon);
	            if((j % 2 == 1 && i % 2 == 1)|| (j % 2 == 0 && i % 2 == 0)) b.setBackground(Color.WHITE);
	            else b.setBackground(Color.gray);
	            b.setOpaque(true);
	            b.setBorderPainted(false);
	            chessBoardSquares[j][i] = b;
	        }
	    }

	    for (int i=0; i < 8; i++) {
	        for (int j=0; j < 8; j++) chessBoard.add(chessBoardSquares[j][i]);
	        
	    }
	}

	public final JComponent getGui() {
	    return gui;
	}
	
	public static void main(String[] args) {
	    Runnable r = new Runnable() {
	        @Override
	        public void run() {
	        	ChessBoard cb = new ChessBoard();
                JFrame f = new JFrame("Chess");
                f.add(cb.getGui());
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                f.setLocationByPlatform(true);
                f.setResizable(false);
                f.pack();
                f.setMinimumSize(f.getSize());
                f.setVisible(true);
            }
        };
        SwingUtilities.invokeLater(r);
	}
		
			//================================Utilize these functions========================================//	
	
	class Piece{
		PlayerColor color;
		PieceType type;
		
		Piece(){
			color = PlayerColor.none;
			type = PieceType.none;
		}
		Piece(PlayerColor color, PieceType type){
			this.color = color;
			this.type = type;
		}
	}
	
	public void setIcon(int x, int y, Piece piece){
		chessBoardSquares[y][x].setIcon(getImageIcon(piece));
		chessBoardStatus[y][x] = piece;
	}
	
	public Piece getIcon(int x, int y){
		return chessBoardStatus[y][x];
	}
	
	public void markPosition(int x, int y){
		chessBoardSquares[y][x].setBackground(Color.pink);
	}
	
	public void unmarkPosition(int x, int y){
		if((y % 2 == 1 && x % 2 == 1)|| (y % 2 == 0 && x % 2 == 0)) chessBoardSquares[y][x].setBackground(Color.WHITE);
		else chessBoardSquares[y][x].setBackground(Color.gray);
	}
	
	public void setStatus(String inpt){
		message.setText(inpt);
	}
	
	public void initiateBoard(){
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++) setIcon(i, j, new Piece());
		}
		setIcon(0, 0, new Piece(PlayerColor.black, PieceType.rook));
		setIcon(0, 1, new Piece(PlayerColor.black, PieceType.knight));
		setIcon(0, 2, new Piece(PlayerColor.black, PieceType.bishop));
		setIcon(0, 3, new Piece(PlayerColor.black, PieceType.queen));
		setIcon(0, 4, new Piece(PlayerColor.black, PieceType.king));
		setIcon(0, 5, new Piece(PlayerColor.black, PieceType.bishop));
		setIcon(0, 6, new Piece(PlayerColor.black, PieceType.knight));
		setIcon(0, 7, new Piece(PlayerColor.black, PieceType.rook));
		for(int i=0;i<8;i++){
			setIcon(1, i, new Piece(PlayerColor.black, PieceType.pawn));
			setIcon(6, i, new Piece(PlayerColor.white, PieceType.pawn));
		}
		setIcon(7, 0, new Piece(PlayerColor.white, PieceType.rook));
		setIcon(7, 1, new Piece(PlayerColor.white, PieceType.knight));
		setIcon(7, 2, new Piece(PlayerColor.white, PieceType.bishop));
		setIcon(7, 3, new Piece(PlayerColor.white, PieceType.queen));
		setIcon(7, 4, new Piece(PlayerColor.white, PieceType.king));
		setIcon(7, 5, new Piece(PlayerColor.white, PieceType.bishop));
		setIcon(7, 6, new Piece(PlayerColor.white, PieceType.knight));
		setIcon(7, 7, new Piece(PlayerColor.white, PieceType.rook));
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++) unmarkPosition(i, j);
		}
		onInitiateBoard();
	}
//======================================================Don't modify above==============================================================//	




//======================================================Implement below=================================================================//		
	enum MagicType {MARK, CHECK, CHECKMATE, TERMINATE};
	public Position white_king, black_king;
	public boolean white_king_moved, black_king_moved, left_black_rook_moved, right_black_rook_moved, left_white_rook_moved, right_white_rook_moved;
	private int selX, selY;
	private int enp_possible;
	public class Position { int x; int y; Position(int x, int y) { this.x = x; this.y = y; } }

	public MagicType status;
	public Piece[][] virtual_board;
	public boolean[][] real_marks, virtual_marks;
	public PlayerColor turn, opp;
	public Queue<Position> list, temp_list;

	class ButtonListener implements ActionListener{
		int x;
		int y;
		ButtonListener(int x, int y){
			this.x = x;
			this.y = y;
		}
		public void actionPerformed(ActionEvent e) {	// Only modify here
			if(!status.equals(MagicType.CHECKMATE) && !status.equals(MagicType.TERMINATE)) {
				if (isAvailableCastling(selX, selY, x, y)) {
					performCastling(selX, selY, x, y);
					procNext();
				} else if (real_marks[y][x]) {
					Piece t = getIcon(x, y);
					updateCastlingMark(selX, selY);
					updateEnp(selX, selY, x, y);
					ctrlKingPos(getIcon(selX, selY), new Position(x, y));
					setIcon(x, y, getIcon(selX, selY));
					setIcon(selX, selY, new Piece());
					if (!t.type.equals(PieceType.king)) status = checkStat();
					else status = MagicType.TERMINATE;
					procNext();
				} else {
					unmarkAll();
					rstSel(-1, -1);
					if(getIcon(x, y).color.equals(turn)) {
						rstSel(x, y);
						enqLst(chessBoardStatus, x, y);
						markLst();
					}
				}
			}
		}
	}
	
	void onInitiateBoard(){
		list = new LinkedList<>(); temp_list = new LinkedList<>();
		status = MagicType.MARK;
		virtual_board = new Piece[8][8];
		for(int i=0; i<8; i++)
			for(int j=0; j<8; j++)
				virtual_board[i][j] = new Piece();
		white_king = new Position(7,4);
		black_king = new Position(0,4);
		virtual_marks = new boolean[8][8]; real_marks = new boolean[8][8];
		unmarkVrt();
		unmarkAll();
		turn = PlayerColor.white; opp = PlayerColor.black;
		rstSel(-1, -1);
		printMsg();
	}
	
	public MagicType isOppCheck() {
		Position king; // position of king
		unmarkVrt();
		if(opp.equals(PlayerColor.black)) { king = new Position(black_king.x, black_king.y); }
		else {  king = new Position(white_king.x, white_king.y); }
		for(int i=0; i<8; i++) {
			for(int j=0; j<8; j++) {
				if(turn.equals(virtual_board[j][i].color)) {
					enqLst(virtual_board, i, j);
					while(!list.isEmpty()) {
						Position element = list.peek();
						list.remove();
						markVrt(element);
					}
				}
			}
		} // end of for
		if(virtual_marks[king.y][king.x]) { return MagicType.CHECK; }
		return MagicType.MARK;
	}
	public boolean isAvailableCastling(int selX, int selY, int x, int y) {
		if(selX > 7 || selY > 7 || x > 7 || y > 7) return false;
		if(selX < 0 || selY < 0 || x < 0 || y < 0) return false;

		PieceType selType = getIcon(selX, selY).type;
		PieceType xType = getIcon(x, y).type;
		PlayerColor selColor = getIcon(selX, selY).color;
		PlayerColor xColor = getIcon(x, y).color;

		// color check
		if(!selColor.equals(turn) || !xColor.equals(turn)) return false;

		int kingX = -1, kingY = -1;
		int rookX = -1, rookY = -1;

		if (selType.equals(PieceType.king) && xType.equals(PieceType.rook)) {
			kingX = selX;
			kingY = selY;
			rookX = x;
			rookY = y;
		} else if (selType.equals(PieceType.rook) && xType.equals(PieceType.king)) {
			kingX = x;
			kingY = y;
			rookX = selX;
			rookY = selY;
		} else {
			return false;
		}
		if (kingX != rookX) return false;

		boolean king_moved = true;
		boolean rook_moved = true;
		if (selColor.equals(PlayerColor.white)) {
			king_moved = white_king_moved;
			if (rookX == 7 && rookY == 0) {
				rook_moved = left_white_rook_moved;
			} else if (rookX == 7 && rookY == 7) {
				rook_moved = right_white_rook_moved;
			} else {
				return false;
			}
		} else {
			king_moved = black_king_moved;
			if (rookX == 0 && rookY == 0) {
				rook_moved = left_black_rook_moved;
			} else if (rookX == 0 && rookY == 7) {
				rook_moved = right_black_rook_moved;
			} else {
				return false;
			}
		}

		if (king_moved || rook_moved) return false;

		// check if there is any piece between king and rook
		for (int i = Math.min(kingY, rookY) + 1; i < Math.max(kingY, rookY); i++) {
			if (!getIcon(kingX, i).type.equals(PieceType.none)) return false;
		}

		// the king is not on the check
		if (!status.equals(MagicType.MARK)) return false;

		// check if the king is on the check when it moves to the castling position
		int checkY = -1;
		if (rookY == 0) {
			// queen-side castling, check (kingX, 2)
			checkY = 2;
		} else {
			// king-side castling, check (kingX, 6)
			checkY = 6;
		}

		int prevKingX = kingX;
		int prevKingY = kingY;

		if (turn.equals(PlayerColor.white)) {
			white_king.x = kingX;
			white_king.y = checkY;
		} else {
			black_king.x = kingX;
			black_king.y = checkY;
		}

		if (!checkStat().equals(MagicType.MARK)) {
			if (turn.equals(PlayerColor.white)) {
				white_king.x = prevKingX;
				white_king.y = prevKingY;
			} else {
				black_king.x = prevKingX;
				black_king.y = prevKingY;
			}
			return false;
		}

        return true;
    }

	public void performCastling(int selX, int selY, int x, int y) {
		int minY = Math.min(selY, y);
		PlayerColor c = getIcon(selX, selY).color;
		if (minY == 0) {
			// queen-side castling
			setIcon(x, 2, new Piece(c, PieceType.king));
			setIcon(x, 3, new Piece(c, PieceType.rook));
		} else {
			// king-side castling
			setIcon(x, 6, new Piece(c, PieceType.king));
			setIcon(x, 5, new Piece(c, PieceType.rook));
		}
		setIcon(x, y, new Piece());
		setIcon(selX, selY, new Piece());
		if (c.equals(PlayerColor.white)) {
			white_king_moved = true;
		} else {
			black_king_moved = true;
		}
	}

	public void updateCastlingMark(int selX, int selY) {
		if (selX == 0 && selY == 0) {
			left_black_rook_moved = true;
		} else if (selX == 0 && selY == 7) {
			right_black_rook_moved = true;
		} else if (selX == 7 && selY == 0) {
			left_white_rook_moved = true;
		} else if (selX == 7 && selY == 7) {
			right_white_rook_moved = true;
		} else if (selX == 0 && selY == 4) {
			black_king_moved = true;
		} else if (selX == 7 && selY == 4) {
			white_king_moved = true;
		}
	}

	public void updateEnp(int selX, int selY, int x, int y) {
		// check if en passant is done
		if (getIcon(selX, selY).type.equals(PieceType.pawn) && Math.abs(selY - y) == 1) {
			if (getIcon(x, y).type.equals(PieceType.none)) {
				setIcon(selX, y, new Piece());
			}
		}

		// en passant possibility
		if (getIcon(selX, selY).type.equals(PieceType.pawn) && Math.abs(selX - x) == 2) {
			if (turn.equals(PlayerColor.white)) {
				enp_possible = y + 8;
			} else {
				enp_possible = y;
			}
		} else {
			enp_possible = -1;
		}
	}

	public void rstSel(int a, int b) {selX = a; selY = b;}
	public void markReal(Position pos) { real_marks[pos.y][pos.x] = true; }
	public void markVrt(Position pos) { virtual_marks[pos.y][pos.x] = true; }
	public void unmarkReal(Position pos) { real_marks[pos.y][pos.x] = false; }
	public void unmarkVrt(Position pos) { virtual_marks[pos.y][pos.x] = false; }

	public void unmarkVrt() {
		for(int i=0; i<8; i++)
			for(int j=0; j<8; j++)
				unmarkVrt(new Position(i, j));
	}

	public void unmarkReal() {
		for(int i=0; i<8; i++)
			for(int j=0; j<8; j++)
				unmarkReal(new Position(i, j));
	}

	public void unmarkAllPos() {
		for(int i=0; i<8; i++)
			for(int j=0; j<8; j++)
				unmarkPosition(i, j);
	}
	public void unmarkAll() { unmarkReal(); unmarkAllPos(); repaintAll();}

	public void repaintAll() {
		for(int i=0; i<8; i++)
			for(int j=0; j<8; j++)
				chessBoardSquares[i][j].repaint();
	}

	public void switchTurn() {
		if(turn.equals(PlayerColor.white)) { turn = PlayerColor.black; opp = PlayerColor.white; }
		else { turn = PlayerColor.white; opp = PlayerColor.black; }
	}

	public void procNext() {
		unmarkAll();
		rstSel(-1, -1);
		pawnPromo();
		switchTurn();
		printMsg();
	}

	public void pawnPromo() {
		for(int i=0; i<8; i++) {
			if(chessBoardStatus[i][7].type.equals(PieceType.pawn) && chessBoardStatus[i][7].color.equals(PlayerColor.black)) {
				setIcon(7, i, new Piece(PlayerColor.black, PieceType.queen));
			}
			if(chessBoardStatus[i][0].type.equals(PieceType.pawn) && chessBoardStatus[i][0].color.equals(PlayerColor.white)) {
				setIcon(0, i, new Piece(PlayerColor.white, PieceType.queen));
			}
		}
	}

	public void enqLst(Piece[][] board, int x, int y) {
		switch(board[y][x].type) {
			case rook:
				enqueue_rook(board, x, y); break;
			case bishop:
				enqueue_bishop(board, x, y); break;
			case queen:
				enqueue_rook(board, x, y);
				enqueue_bishop(board, x, y);
				break;
			case king:
				for(int i=x-1; i<=x+1; i++)
					for(int j=y-1; j<=y+1; j++)
						if(is_valid_place_for_king(i, j, board, board[y][x].color))
							list.add(new Position(i, j));
				break;
			case pawn:
				// move forward 2 steps
				if(x==6 && board[y][x].color.equals(PlayerColor.white)) {
					if(board[y][x-2].type.equals(PieceType.none) && board[y][x-1].type.equals(PieceType.none))
						list.add(new Position(x-2, y));
				}
				else if(x==1 && board[y][x].color.equals(PlayerColor.black)) {
					if(board[y][x+2].type.equals(PieceType.none) && board[y][x+1].type.equals(PieceType.none))
						list.add(new Position(x+2, y));
				}
				// move forward 1 step
				if(board[y][x].color.equals(PlayerColor.white)) {
					if(x!=0) {
						if(board[y][x-1].type.equals(PieceType.none))
							list.add(new Position(x-1, y));
						if(y>0 && board[y-1][x-1].color.equals(PlayerColor.black))
							list.add(new Position(x-1, y-1));
						if(y<7 && board[y+1][x-1].color.equals(PlayerColor.black))
							list.add(new Position(x-1, y+1));
					}
				}
				else if(board[y][x].color.equals(PlayerColor.black)){
					if(x!=7) {
						if (board[y][x + 1].type.equals(PieceType.none))
							list.add(new Position(x + 1, y));
						if (y > 0 && board[y - 1][x + 1].color.equals(PlayerColor.white))
							list.add(new Position(x + 1, y - 1));
						if (y < 7 && board[y + 1][x + 1].color.equals(PlayerColor.white))
							list.add(new Position(x + 1, y + 1));
					}
				}
				// en passant
				int enp_temp = -1;
				if (enp_possible > 7) {
					enp_temp = enp_possible - 8;
				} else {
					enp_temp = enp_possible;
				}
				if((enp_possible != -1) && (board[enp_temp][x].type.equals(PieceType.pawn))) {
					if(board[y][x].color.equals(PlayerColor.white) && (enp_possible < 8)) {
						if(x==3 && ((enp_temp == y-1) || (enp_temp == y+1))) {
							if (board[enp_temp][x-1].type.equals(PieceType.none))
								list.add(new Position(x-1, enp_temp));
						}
					}
					else if(board[y][x].color.equals(PlayerColor.black) && (enp_possible > 7)) {
						if(x==4 && ((enp_temp == y-1) || (enp_temp == y+1))) {
							if (board[enp_temp][x+1].type.equals(PieceType.none))
								list.add(new Position(x+1, enp_temp));
						}
					}
				}
				break;
			case knight:
				PlayerColor origin_color = board[y][x].color;
				if(is_valid_place_for_knight(x+1, y+2, board,  origin_color))
					list.add(new Position(x+1, y+2));
				if(is_valid_place_for_knight(x+2, y+1, board,  origin_color))
					list.add(new Position(x+2, y+1));
				if(is_valid_place_for_knight(x-1, y-2, board,  origin_color))
					list.add(new Position(x-1, y-2));
				if(is_valid_place_for_knight(x-2, y-1, board,  origin_color))
					list.add(new Position(x-2, y-1));
				if(is_valid_place_for_knight(x-2, y+1, board,  origin_color))
					list.add(new Position(x-2, y+1));
				if(is_valid_place_for_knight(x+2, y-1, board,  origin_color))
					list.add(new Position(x+2, y-1));
				if(is_valid_place_for_knight(x+1, y-2, board,  origin_color))
					list.add(new Position(x+1, y-2));
				if(is_valid_place_for_knight(x-1, y+2, board,  origin_color))
					list.add(new Position(x-1, y+2));
				break;
		}
	}

	public void enqueue_rook(Piece[][] board, int x, int y) {
		int i, j; PlayerColor origin_color = board[y][x].color;
		i=x+1;
		while(is_valid_place_for_others(i,y)) {
			if(board[y][i].color.equals(origin_color)) break;
			else if(board[y][i].type.equals(PieceType.none)) list.add(new Position(i, y));
			else { list.add(new Position(i, y)); break; }
			i++;
		}
		i=x-1;
		while(is_valid_place_for_others(i,y)) {
			if(board[y][i].color.equals(origin_color)) break;
			else if(board[y][i].type.equals(PieceType.none)) list.add(new Position(i, y));
			else { list.add(new Position(i, y)); break; }
			i--;
		}
		j=y+1;
		while(is_valid_place_for_others(x,j)) {
			if(board[j][x].color.equals(origin_color)) break;
			else if(board[j][x].type.equals(PieceType.none)) list.add(new Position(x, j));
			else { list.add(new Position(x, j)); break; }
			j++;
		}
		j=y-1;
		while(is_valid_place_for_others(x,j)) {
			if(board[j][x].color.equals(origin_color)) break;
			else if(board[j][x].type.equals(PieceType.none)) list.add(new Position(x, j));
			else { list.add(new Position(x, j)); break; }
			j--;
		}
	}

	public void enqueue_bishop(Piece[][] board, int x, int y) {
		int i, j; PlayerColor origin_color = board[y][x].color;
		i=x+1; j=y+1;
		while(is_valid_place_for_others(i,j)) {
			if(board[j][i].color.equals(origin_color)) break;
			else if(board[j][i].type.equals(PieceType.none)) list.add(new Position(i, j));
			else { list.add(new Position(i, j)); break; }
			i++; j++;
		}
		i=x-1; j=y+1;
		while(is_valid_place_for_others(i,j)) {
			if(board[j][i].color.equals(origin_color)) break;
			else if(board[j][i].type.equals(PieceType.none)) list.add(new Position(i, j));
			else { list.add(new Position(i, j)); break; }
			i--; j++;
		}
		i=x+1; j=y-1;
		while(is_valid_place_for_others(i,j)) {
			if(board[j][i].color.equals(origin_color)) break;
			else if(board[j][i].type.equals(PieceType.none)) list.add(new Position(i, j));
			else { list.add(new Position(i, j)); break; }
			i++; j--;
		}
		i=x-1; j=y-1;
		while(is_valid_place_for_others(i,j)) {
			if(board[j][i].color.equals(origin_color)) break;
			else if(board[j][i].type.equals(PieceType.none)) list.add(new Position(i, j));
			else { list.add(new Position(i, j)); break; }
			i--; j--;
		}
	}

	public boolean is_valid_place_for_king(int x, int y, Piece[][] board, PlayerColor origin_color) {
		boolean flag = true;
		if(x<0 || x>7) return false;
		if(y<0 || y>7) return false;
		PlayerColor color = board[y][x].color;
		if(color.equals(origin_color)) flag = false;
		return flag;
	}

	public boolean is_valid_place_for_knight(int x, int y, Piece[][] board, PlayerColor origin_color) {
		if(is_valid_place_for_others(x, y) && !(origin_color.equals(board[y][x].color))) return true;
		else return false;
	}

	public boolean is_valid_place_for_others(int x, int y) {
		if(x<0 || x>7) return false;
		else if(y<0 || y>7) return false;
		else return true;
	}

	public void printMsg() {
		if(turn.equals(PlayerColor.white)) {
			if(status.equals(MagicType.MARK)) setStatus("WHITE's TURN");
			else if(status.equals(MagicType.CHECK)) setStatus("WHITE's TURN/CHECK");
			else if(status.equals(MagicType.CHECKMATE)) setStatus("WHITE's TURN/CHECKMATE");
			else setStatus("BLACK WINS");
		} else {
			if(status.equals(MagicType.MARK)) setStatus("BLACK's TURN");
			else if(status.equals(MagicType.CHECK)) setStatus("BLACK's TURN/CHECK");
			else if(status.equals(MagicType.CHECKMATE)) setStatus("BLACK's TURN/CHECKMATE");
			else setStatus("WHITE WINS");
		}
	}

	public void markLst() {
		while (!list.isEmpty()) {
			Position element = list.peek();
			list.remove();
			markPosition(element.x, element.y);
			markReal(element);
		}
	}

	public void list2temp_list() {
		while(!list.isEmpty()) {
			temp_list.add(list.peek());
			list.remove();
		}
	}

	public void ctrlKingPos(Piece piece, Position target) {
		if(piece.type.equals(PieceType.king) && piece.color.equals(PlayerColor.black)) { black_king.x = target.x; black_king.y = target.y; }
		else if(piece.type.equals(PieceType.king) && piece.color.equals(PlayerColor.white)) { white_king.x = target.x; white_king.y = target.y; }
	}

	public MagicType checkStat() {
		for(int i=0; i<8; i++)
			for(int j=0;j<8; j++)
				virtual_board[j][i] = getIcon(i, j);
		if(!isOppCheck().equals(MagicType.CHECK)) return MagicType.MARK;
		else {
			unmarkVrt();
			boolean is_checkmate = true;
			for(int i=0; i<8; i++) {
				for(int j=0; j<8; j++) {
					if(opp.equals(virtual_board[j][i].color)) {
						enqLst(virtual_board, i, j);
						list2temp_list();
						while(!temp_list.isEmpty()) {
							Position element = temp_list.peek();
							temp_list.remove();
							Position pos = new Position(i, j);
							Piece t1 = virtual_board[pos.y][pos.x]; Piece t2 = virtual_board[element.y][element.x];
							ctrlKingPos(t1, element);
							virtual_board[element.y][element.x] = t1;
							virtual_board[pos.y][pos.x] = new Piece();
							if(isOppCheck().equals(MagicType.MARK)) is_checkmate = false;
							virtual_board[pos.y][pos.x] = t1;
							virtual_board[element.y][element.x] = t2;
							ctrlKingPos(t1, pos);
						}
					}
				}
			} // end of for
			if(is_checkmate) return MagicType.CHECKMATE;
			else return MagicType.CHECK;
		}
	}
}