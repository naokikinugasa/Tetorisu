package main;
public class Setting implements Tetorisu{
	public static int fall;
	public static int side;
	public static int gameover_flag;
	public static int ran;
	public static int turn_point = 1000000000;
	public static int side_flag = 0;
	
	public static void make_block() {
		int x,y;
		int block_flag = 0;
		if(block_flag == 0){
			for(y=0;y<BLOCK_HEIGHT;y++){
				for(x=0;x<BLOCK_WIDTH;x++){
					block[y][x] = blocks[block_index_y[ran] + y][block_index_x[0] + x];
				}
			}
			block_flag = 1;
		}
	}
	
	public static void make_field(){
		int i,j,x,y;
		for(y=0;y<BLOCK_HEIGHT;y++){
			for(x=0;x<BLOCK_WIDTH;x++){
				field[y+fall][x+side] = block[y][x];
			}
		}
		for(i=0;i<FIELD_HEIGHT;i++){
			for(j=0;j<FIELD_WIDTH;j++){
				field[i][j] = field[i][j] + stage[i][j];

			}
		}
	}
	
	public static void init_field(){
		int i,j;
		for(i=0;i<FIELD_HEIGHT;i++){
			for(j=0;j<FIELD_WIDTH;j++){
				field[i][0] = 9;
				field[i][1] = 9;
				field[i][2] = 9;
				field[20][j] = 9;
				field[21][j] = 9;
				field[22][j] = 9;
				field[i][13] = 9;
				field[i][14] = 9;
				field[i][15] = 9;
			}
		}
	}
	
	public static void init_var(){
		fall = 0;
		side = 4;
	}
	
	public static void clear_field(){
		int i,j;
		for(i=0;i<FIELD_HEIGHT;i++){
			for(j=0;j<FIELD_WIDTH;j++){
				field[i][j] = 0;
			}
		}
	}
	
	public static void make_collision_field(){
		int i,j;
		for(i=0;i<FIELD_HEIGHT;i++){
			for(j=0;j<FIELD_WIDTH;j++){
				collision_field[i][j] = stage[i][j];
				collision_field[i][0] = 9;
				collision_field[i][1] = 9;
				collision_field[i][2] = 9;
				collision_field[20][j] = 9;
				collision_field[21][j] = 9;
				collision_field[22][j] = 9;
				collision_field[i][13] = 9;
				collision_field[i][14] = 9;
				collision_field[i][15] = 9;
			}
		}	
	}
	
	public static void freeze_block(){
		 int x,y;
		 int freeze_flag = 0;

		 make_collision_field();

		 for(y=0;y<BLOCK_HEIGHT;y++){
		  for(x=0;x<BLOCK_WIDTH;x++){
		   if(block[y][x] != 0){
		    if(collision_field[fall + (y + 1)][side + x] != 0){
		     freeze_flag++;
		    }
		   }
		  }
		 }
		 if(freeze_flag != 0){
		  search_line();
		  save_field();
		  init_var();
		  ran = (int)(Math.random()*BLOCK_NUM);
		  make_block();
		 }
	}
	
	public static void save_field(){
		int i,j;
		for(i=0;i<FIELD_HEIGHT;i++){
			for(j=0;j<FIELD_WIDTH;j++){
				stage[i][j] = field[i][j];
			}
		}
	}

	public static void gameover(){
		 int x,y;
		
		 make_collision_field();
		
		 for(y=0;y<BLOCK_HEIGHT;y++){
		  for(x=0;x<BLOCK_WIDTH;x++){
		   if(block[y][x] != 0){
		    if(collision_field[fall + y][side + x] != 0){
		     gameover_flag++;
		    }
		   }
		  }
		 }
	}
	
	public static void search_line(){
		int i,j;
		int zero_count = 0;
		int clear_flag = 0;
		int[] clear_lines_point = new int[4];
		int clear_line_index = 0;
		int[] remain_lines_point = new int[20];
		int remain_line_index = 0;

		for(i=SEARCH_START_Y;i>=SEARCH_START_Y-19;i--){
			for(j=SEARCH_START_X;j<SEARCH_START_X+10;j++){
				if(field[i][j] == 0){
					zero_count++;
				}
			}
			if(zero_count == 0){
				clear_flag++;
				clear_lines_point[clear_line_index] = i;
				clear_line_index++;
			}
			else{
				remain_lines_point[remain_line_index] = i;
				remain_line_index++;
				zero_count = 0;
			}
			
		}

		if(clear_flag != 0){
			for(clear_line_index=0;clear_line_index<4;clear_line_index++){
				if(clear_lines_point[clear_line_index] != 0){
					for(j=SEARCH_START_X;j<SEARCH_START_X+10;j++){
						field[clear_lines_point[clear_line_index]][j] = 0;
					}
				}
			}

			remain_line_index = 0;
			for(i=SEARCH_START_Y;i>=SEARCH_START_Y-19;i--){
				for(j=SEARCH_START_X;j<SEARCH_START_X+10;j++){
					field[i][j] = field[remain_lines_point[remain_line_index]][j];
				}
				remain_line_index++;
			}		
		}
	}
	
	public static void turn(int rl){
		int x,y;
		int turn_flag = 0;
		int[][] turn_block = new int[4][4];

		turn_point += rl;

		make_collision_field();

		for(y=0;y<BLOCK_HEIGHT;y++){
			for(x=0;x<BLOCK_WIDTH;x++){
				turn_block[y][x] = blocks[block_index_y[ran] + y][block_index_x[turn_point % 4] + x];
			}
		}

		for(y=0;y<BLOCK_HEIGHT;y++){
			for(x=0;x<BLOCK_WIDTH;x++){
				if(turn_block[y][x] != 0){
					if(collision_field[fall + y][side + x] != 0){
						turn_flag++;
					}
				}
			}
		}

		if(turn_flag == 0){
			for(y=0;y<BLOCK_HEIGHT;y++){
				for(x=0;x<BLOCK_WIDTH;x++){
					block[y][x] = turn_block[y][x];
				}
			}
		}else{
			turn_point -= rl;
		}
	}
	
	public static void side_check(int rl) {
		int x,y;
		for(y=0;y<BLOCK_HEIGHT;y++){
			for(x=0;x<BLOCK_WIDTH;x++){
		     if(block[y][x] != 0){
		      if(collision_field[fall + y][side + (x + rl)] != 0){
		       side_flag++;
		      }
		     }
		    }
		}
		if(side_flag == 0){
		    side += rl;
		}else{
			side_flag = 0;
		}
	}


	
}
