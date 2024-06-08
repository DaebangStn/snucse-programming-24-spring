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
	private int selX, selY;
	private boolean check, checkmate, end;
	public class Position { int x; int y; Position(int x, int y) { this.x = x; this.y = y; } }

	public MagicType status;
	public Piece[][] virtual_board;
	public boolean[][] real_marks, virtual_marks;
	public PlayerColor turn, opponent;
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
				if (real_marks[y][x]) {
					Piece t = getIcon(x, y);
					control_Pos_iff_king(getIcon(selX, selY), new Position(x, y));
					setIcon(x, y, getIcon(selX, selY));
					setIcon(selX, selY, new Piece());
					if(!t.type.equals(PieceType.king)) status = check_status();
					else status = MagicType.TERMINATE;
					proceed_next_turn();
				} else {
					unmark_all_real_and_position();
					reset_selected(-1, -1);
					if(getIcon(x, y).color.equals(turn)) {
						reset_selected(x, y);
						enqueue_list(chessBoardStatus, x, y);
						mark_from_list();
					}
				}
			}
		}
	}
	
	void onInitiateBoard(){
		list = new LinkedList<>(); temp_list = new LinkedList<>();
		status = ChessBoard.MagicType.MARK;
		virtual_board = new ChessBoard.Piece[8][8];
		for(int i=0; i<8; i++)
			for(int j=0; j<8; j++)
				virtual_board[i][j] = new ChessBoard.Piece();
		white_king = new ChessBoard.Position(7,4);
		black_king = new ChessBoard.Position(0,4);
		virtual_marks = new boolean[8][8]; real_marks = new boolean[8][8];
		unmark_all_virtual();
		turn = PlayerColor.black; opponent = PlayerColor.white;
		reset_selected(-1, -1);
		display_msg();
	}
	
	public MagicType is_opponent_check() {
		Position king; // position of king
		unmark_all_virtual();
		if(opponent.equals(PlayerColor.black)) { king = new ChessBoard.Position(black_king.x, black_king.y); }
		else {  king = new ChessBoard.Position(white_king.x, white_king.y); }
		for(int i=0; i<8; i++) {
			for(int j=0; j<8; j++) {
				if(turn.equals(virtual_board[j][i].color)) {
					enqueue_list(virtual_board, i, j);
					while(!list.isEmpty()) {
						ChessBoard.Position element = list.peek();
						list.remove();
						mark_Pos_virtual(element);
					}
				}
			}
		} // end of for
		if(virtual_marks[king.y][king.x]) { return ChessBoard.MagicType.CHECK; }
		return ChessBoard.MagicType.MARK;
	}
	public void reset_selected(int a, int b) {selX = a; selY = b;}
	public void mark_Pos_real(ChessBoard.Position pos) { real_marks[pos.y][pos.x] = true; }
	public void mark_Pos_virtual(ChessBoard.Position pos) { virtual_marks[pos.y][pos.x] = true; }
	public void unmark_Pos_real(ChessBoard.Position pos) { real_marks[pos.y][pos.x] = false; }
	public void unmark_Pos_virtual(ChessBoard.Position pos) { virtual_marks[pos.y][pos.x] = false; }
	public void unmark_all_virtual() {
		for(int i=0; i<8; i++)
			for(int j=0; j<8; j++)
				unmark_Pos_virtual(new ChessBoard.Position(i, j));
	}
	public void unmark_all_real() {
		for(int i=0; i<8; i++)
			for(int j=0; j<8; j++)
				unmark_Pos_real(new ChessBoard.Position(i, j));
	}
	public void unmark_all_position() {
		for(int i=0; i<8; i++)
			for(int j=0; j<8; j++)
				unmarkPosition(i, j);
	}
	public void unmark_all_real_and_position() { unmark_all_real(); unmark_all_position();}

	public void switch_turn() {
		if(turn.equals(PlayerColor.white)) { turn = PlayerColor.black; opponent = PlayerColor.white; }
		else { turn = PlayerColor.white; opponent = PlayerColor.black; }
	}
	public void proceed_next_turn() {
		unmark_all_real_and_position();
		reset_selected(-1, -1);
		switch_turn();
		display_msg();
	}

	public void enqueue_list(ChessBoard.Piece[][] board, int x, int y) {
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
							list.add(new ChessBoard.Position(i, j));
				break;
			case pawn:
				if(x==6 && board[y][x].color.equals(PlayerColor.white)) {
					if(board[y][x-2].type.equals(PieceType.none) && board[y][x-1].type.equals(PieceType.none))
						list.add(new ChessBoard.Position(x-2, y));
				}
				else if(x==1 && board[y][x].color.equals(PlayerColor.black)) {
					if(board[y][x+2].type.equals(PieceType.none) && board[y][x+1].type.equals(PieceType.none))
						list.add(new ChessBoard.Position(x+2, y));
				}
				////////////////////// 2칸 앞으로 특수규칙 종료 ///////////////
				if(board[y][x].color.equals(PlayerColor.white)) {
					if(x!=0) {
						if(board[y][x-1].type.equals(PieceType.none))
							list.add(new ChessBoard.Position(x-1, y));
						if(y>0 && board[y-1][x-1].color.equals(PlayerColor.black))
							list.add(new ChessBoard.Position(x-1, y-1));
						if(y<7 && board[y+1][x-1].color.equals(PlayerColor.black))
							list.add(new ChessBoard.Position(x-1, y+1));
					}
				}
				else if(board[y][x].color.equals(PlayerColor.black)){
					if(x!=7) {
						if (board[y][x + 1].type.equals(PieceType.none))
							list.add(new ChessBoard.Position(x + 1, y));
						if (y > 0 && board[y - 1][x + 1].color.equals(PlayerColor.white))
							list.add(new ChessBoard.Position(x + 1, y - 1));
						if (y < 7 && board[y + 1][x + 1].color.equals(PlayerColor.white))
							list.add(new ChessBoard.Position(x + 1, y + 1));
					}
				}
				break;
			case knight:
				PlayerColor origin_color = board[y][x].color;
				if(is_valid_place_for_knight(x+1, y+2, board,  origin_color))
					list.add(new ChessBoard.Position(x+1, y+2));
				if(is_valid_place_for_knight(x+2, y+1, board,  origin_color))
					list.add(new ChessBoard.Position(x+2, y+1));
				if(is_valid_place_for_knight(x-1, y-2, board,  origin_color))
					list.add(new ChessBoard.Position(x-1, y-2));
				if(is_valid_place_for_knight(x-2, y-1, board,  origin_color))
					list.add(new ChessBoard.Position(x-2, y-1));
				if(is_valid_place_for_knight(x-2, y+1, board,  origin_color))
					list.add(new ChessBoard.Position(x-2, y+1));
				if(is_valid_place_for_knight(x+2, y-1, board,  origin_color))
					list.add(new ChessBoard.Position(x+2, y-1));
				if(is_valid_place_for_knight(x+1, y-2, board,  origin_color))
					list.add(new ChessBoard.Position(x+1, y-2));
				if(is_valid_place_for_knight(x-1, y+2, board,  origin_color))
					list.add(new ChessBoard.Position(x-1, y+2));
				break;
		}
	}
	public void enqueue_rook(ChessBoard.Piece[][] board, int x, int y) {
		int i, j; PlayerColor origin_color = board[y][x].color;
		i=x+1;
		while(is_valid_place_for_others(i,y)) {
			if(board[y][i].color.equals(origin_color)) break;
			else if(board[y][i].type.equals(PieceType.none)) list.add(new ChessBoard.Position(i, y));
			else { list.add(new ChessBoard.Position(i, y)); break; }
			i++;
		}
		i=x-1;
		while(is_valid_place_for_others(i,y)) {
			if(board[y][i].color.equals(origin_color)) break;
			else if(board[y][i].type.equals(PieceType.none)) list.add(new ChessBoard.Position(i, y));
			else { list.add(new ChessBoard.Position(i, y)); break; }
			i--;
		}
		j=y+1;
		while(is_valid_place_for_others(x,j)) {
			if(board[j][x].color.equals(origin_color)) break;
			else if(board[j][x].type.equals(PieceType.none)) list.add(new ChessBoard.Position(x, j));
			else { list.add(new ChessBoard.Position(x, j)); break; }
			j++;
		}
		j=y-1;
		while(is_valid_place_for_others(x,j)) {
			if(board[j][x].color.equals(origin_color)) break;
			else if(board[j][x].type.equals(PieceType.none)) list.add(new ChessBoard.Position(x, j));
			else { list.add(new ChessBoard.Position(x, j)); break; }
			j--;
		}
	}
	public void enqueue_bishop(ChessBoard.Piece[][] board, int x, int y) {
		int i, j; PlayerColor origin_color = board[y][x].color;
		i=x+1; j=y+1;
		while(is_valid_place_for_others(i,j)) {
			if(board[j][i].color.equals(origin_color)) break;
			else if(board[j][i].type.equals(PieceType.none)) list.add(new ChessBoard.Position(i, j));
			else { list.add(new ChessBoard.Position(i, j)); break; }
			i++; j++;
		}
		i=x-1; j=y+1;
		while(is_valid_place_for_others(i,j)) {
			if(board[j][i].color.equals(origin_color)) break;
			else if(board[j][i].type.equals(PieceType.none)) list.add(new ChessBoard.Position(i, j));
			else { list.add(new ChessBoard.Position(i, j)); break; }
			i--; j++;
		}
		i=x+1; j=y-1;
		while(is_valid_place_for_others(i,j)) {
			if(board[j][i].color.equals(origin_color)) break;
			else if(board[j][i].type.equals(PieceType.none)) list.add(new ChessBoard.Position(i, j));
			else { list.add(new ChessBoard.Position(i, j)); break; }
			i++; j--;
		}
		i=x-1; j=y-1;
		while(is_valid_place_for_others(i,j)) {
			if(board[j][i].color.equals(origin_color)) break;
			else if(board[j][i].type.equals(PieceType.none)) list.add(new ChessBoard.Position(i, j));
			else { list.add(new ChessBoard.Position(i, j)); break; }
			i--; j--;
		}
	}

	public boolean is_valid_place_for_king(int x, int y, ChessBoard.Piece[][] board, PlayerColor origin_color) {
		boolean flag = true;
		if(x<0 || x>7) return false;
		if(y<0 || y>7) return false;
		PlayerColor color = board[y][x].color;
		if(color.equals(origin_color)) flag = false;
		return flag;
	}
	public boolean is_valid_place_for_knight(int x, int y, ChessBoard.Piece[][] board, PlayerColor origin_color) {
		if(is_valid_place_for_others(x, y) && !(origin_color.equals(board[y][x].color))) return true;
		else return false;
	}
	public boolean is_valid_place_for_others(int x, int y) {
		if(x<0 || x>7) return false;
		else if(y<0 || y>7) return false;
		else return true;
	}
	public void display_msg() {
		if(turn.equals(PlayerColor.white)) {
			if(status.equals(ChessBoard.MagicType.MARK)) setStatus("WHITE's TURN");
			else if(status.equals(ChessBoard.MagicType.CHECK)) setStatus("WHITE's TURN/CHECK");
			else if(status.equals(ChessBoard.MagicType.CHECKMATE)) setStatus("WHITE's TURN/CHECKMATE");
			else setStatus("BLACK WINS");
		} else {
			if(status.equals(ChessBoard.MagicType.MARK)) setStatus("BLACK's TURN");
			else if(status.equals(ChessBoard.MagicType.CHECK)) setStatus("BLACK's TURN/CHECK");
			else if(status.equals(ChessBoard.MagicType.CHECKMATE)) setStatus("BLACK's TURN/CHECKMATE");
			else setStatus("WHITE WINS");
		}
	}
	public void mark_from_list() {
		while (!list.isEmpty()) {
			ChessBoard.Position element = list.peek();
			list.remove();
			markPosition(element.x, element.y);
			mark_Pos_real(element);
		}
	}
	public void list2temp_list() {
		while(!list.isEmpty()) {
			temp_list.add(list.peek());
			list.remove();
		}
	}

	public void control_Pos_iff_king(ChessBoard.Piece piece, ChessBoard.Position target) {
		if(piece.type.equals(PieceType.king) && piece.color.equals(PlayerColor.black)) { black_king.x = target.x; black_king.y = target.y; }
		else if(piece.type.equals(PieceType.king) && piece.color.equals(PlayerColor.white)) { white_king.x = target.x; white_king.y = target.y; }
	}

	public ChessBoard.MagicType check_status() {
		for(int i=0; i<8; i++)
			for(int j=0;j<8; j++)
				virtual_board[j][i] = getIcon(i, j);
		if(!is_opponent_check().equals(ChessBoard.MagicType.CHECK)) return ChessBoard.MagicType.MARK;
		else {
			unmark_all_virtual();
			boolean is_checkmate = true;
			for(int i=0; i<8; i++) {
				for(int j=0; j<8; j++) {
					if(opponent.equals(virtual_board[j][i].color)) {
						enqueue_list(virtual_board, i, j);
						list2temp_list();
						while(!temp_list.isEmpty()) {
							ChessBoard.Position element = temp_list.peek();
							temp_list.remove();
							ChessBoard.Position pos = new ChessBoard.Position(i, j);
							ChessBoard.Piece t1 = virtual_board[pos.y][pos.x]; ChessBoard.Piece t2 = virtual_board[element.y][element.x];
							control_Pos_iff_king(t1, element);
							virtual_board[element.y][element.x] = t1;
							virtual_board[pos.y][pos.x] = new ChessBoard.Piece();
							if(is_opponent_check().equals(ChessBoard.MagicType.MARK)) is_checkmate = false;
							virtual_board[pos.y][pos.x] = t1;
							virtual_board[element.y][element.x] = t2;
							control_Pos_iff_king(t1, pos);
						}
					}
				}
			} // end of for
			if(is_checkmate) return ChessBoard.MagicType.CHECKMATE;
			else return ChessBoard.MagicType.CHECK;
		}
	}

}