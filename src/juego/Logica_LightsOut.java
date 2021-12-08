package juego;

import java.util.Random;

public class Logica_LightsOut {
	
	//Variables
	private boolean[][] tablero;
	private int dimension;
	private int turnos;
	
	
	/** Inicializa un tablero de tama�o: dimension*dimension */
	public Logica_LightsOut(int dimension) {
		this.tablero = new boolean[dimension][dimension]; 
		this.dimension = dimension;
		this.turnos = 0;
		configurarTableroRandom(dimension);
	}
	
	/** Genera booleanos al azar para cada celda del tablero */
	private void configurarTableroRandom(int dimension) {
		Random random = new Random(); 
		int fila = 0;
		int col = 0;
		for (int i = 0; i <(dimension*dimension) ; ++i) {
			if(col<dimension) {
				if(random.nextBoolean())
					cambiarEstado(fila, col);
			}	
			else {
				col=0;
				fila++;
			}	
			col++;
		}
	}
	
	/** Cambia el estado del booleano indicado como parametro */
	public void cambiarEstado(int fila, int col) {
		tablero[fila][col] = !tablero[fila][col];
	}

	/** Dada una posicion devuelve en un arrglo los indices (i,j) 
	 * para ubicar si respectiva posicion dentro de la matriz */
	public int[] buscarIndices(int pos) {
		int[] indices = new int[2];
		
		int fila = (int) (pos/this.dimension); 
		int col = pos % this.dimension;
		
		indices[0] = fila;
		indices[1] = col;
		
		return indices;
	}

	/** Dados los indices de un elemento de la matriz, cambia el estado 
	 * de los vecinos correspondientes (arriba, abajo, der, izq) */
	public void cambiarVecinos(int fila, int col) {
		//exception indices fuera de matriz
		if (fila<0 || fila>= this.dimension || col<0 || col>= this.dimension)
			throw new RuntimeException("Los indices deben ser enteros entre 0 y la dimension -1");
		//cambiar el estado del vecino de arriba (1 fila anterior)
		if(fila != 0) {
			cambiarEstado(fila-1, col);
		}
		//cambiar el estado del vecino de abajo (1 fila posterior)
		if(fila!= (this.dimension -1)) {
			cambiarEstado(fila+1,col);
		}
			//cambiar el estado del vecino de la izquierda (1 columna anterior)
		if(col != 0) {
			cambiarEstado(fila,col-1);
		}
		//cambiar el estado del vecino de la derecha (1 columna posterior)
		if(col!= (this.dimension -1)) {
			cambiarEstado(fila,col+1);
		}
	}
	
	/** Por cada luz apagada manualmente, incrementa en uno el contador de turnos */
	public void aumentarTurno() {
		this.turnos ++ ; 
	}
	
	/** Jugar una partida, dada la posicion del boton seleccionado apaga esa luz y las luces adyacentes*/
	public void jugarUnaPartida(int posicion){
		int[] idxBoton = buscarIndices(posicion);
		int fila = idxBoton[0];
		int col = idxBoton[1];
		cambiarEstado(fila,col);
		this.cambiarVecinos(fila, col);
		this.aumentarTurno();
	}
	
	/** Devuelve la cantidad de turnos */
	public int cantidadTurnos () {
		return turnos;
	}
	
	/** Retorna el valor booleano de una posicion del tablero, es decir si la luz esta encendida o apagada */
	public boolean estaEncendida (int fila, int col) {
		return this.tablero[fila][col];
	}
}
