/**
 * Autor - Ibai Andreu
 * Punto de entrada a la aplicación
 */
public class TestGestorFaltas {
    /**
     * Se acepta como argumento del main() el nº máximo de estudiantes
     * y una vez creado el gestor de faltas se muestra la información solicitada
     * (ver enunciado)
     */
    public static void main(String[] args) {
        GestorFaltas gestorFaltas = new GestorFaltas(10);
        
        gestorFaltas.leerDeFichero();
        System.out.println(gestorFaltas.toString());
    }

}
