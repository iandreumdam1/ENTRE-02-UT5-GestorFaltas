import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Autor - Ibai Andreu
 * Un objeto de esta clase permite registrar estudiantes de un
 * curso (leyendo la informaci�n de un fichero de texto) y 
 * emitir listados con las faltas de los estudiantes, justificar faltas, 
 * anular matr�cula dependiendo del n� de faltas, .....
 *
 */
public class GestorFaltas {
    private Estudiante[] estudiantes;
    private int total;

    public GestorFaltas(int n) {
         estudiantes = new Estudiante[n];
    }

    /**
     * Devuelve true si el array de estudiantes est� completo,
     * false en otro caso
     */
    public boolean cursoCompleto() {
        return total >= estudiantes.length;
    }

    /**
     *    A�ade un nuevo estudiante solo si el curso no est� completo y no existe ya otro
     *    estudiante igual (con los mismos apellidos). 
     *    Si no se puede a�adir se muestra los mensajes adecuados 
     *    (diferentes en cada caso)
     *    
     *    El estudiante se a�ade de tal forma que queda insertado en orden alfab�tico de apellidos
     *    (de menor a mayor)
     *    !!OJO!! No hay que ordenar ni utilizar ning�n algoritmo de ordenaci�n
     *    Hay que insertar en orden 
     *    
     */
    public void addEstudiante(Estudiante nuevo) {
         if(!cursoCompleto()){
             if(buscarEstudiante(nuevo.getApellidos()) == -1){
               for(int i = 0; i < total; i++){
                   estudiantes[i].setApellidos(nuevo.getApellidos());
               }
            }
            else{
               System.out.println("No se puede a�adir el alumno. Nombre repetido."); 
            }
        }
        else if(!cursoCompleto() && buscarEstudiante(nuevo.getApellidos()) >= 0){
            System.out.println("No se puede a�adir el alumno. Curso completo y nombre repetido.");
        } 
        else{
            System.out.println("No se puede a�adir el alumno. Curso completo.");
        }
    }


    /**
     * buscar un estudiante por sus apellidos
     * Si est� se devuelve la posici�n, si no est� se devuelve -1
     * Es indiferente may�sculas / min�sculas
     * Puesto que el curso est� ordenado por apellido haremos la b�squeda m�s
     * eficiente
     *  
     */
    public int buscarEstudiante(String apellidos) {
        int encontrado = -1;
        int izquierda = 0;
        int derecha = total;
        while (izquierda <= derecha){
            int mitad = (izquierda + derecha) / 2;
            if (estudiantes[mitad].getApellidos().compareTo(apellidos) < 0){
                encontrado = mitad;
            }
            else if (estudiantes[mitad].getApellidos().compareTo(apellidos) > 0){
                derecha = mitad - 1;                
            }
            else{
                izquierda = mitad + 1;
            }
        }
        return encontrado;
    }

    /**
     * Representaci�n textual del curso
     * Utiliza StringBuilder como clase de apoyo.
     *  
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < total; i++){
            sb.append(estudiantes[i].toString()).append("\t");
        }
        return sb.toString();

    }

    /**
     *  Se justifican las faltas del estudiante cuyos apellidos se proporcionan
     *  El m�todo muestra un mensaje indicando a qui�n se ha justificado las faltas
     *  y cu�ntas
     *  
     *  Se asume todo correcto (el estudiante existe y el n� de faltas a
     *  justificar tambi�n)
     */
    public void justificarFaltas(String apellidos, int faltas) {
        estudiantes[buscarEstudiante(apellidos)].justificar(faltas);
        System.out.println("Al alumno" + estudiantes[buscarEstudiante(apellidos)].getNombre() + 
        estudiantes[buscarEstudiante(apellidos)].getApellidos() + 
        "Se le han justificado " + faltas + " faltas.");

    }

    /**
     * ordenar los estudiantes de mayor a menor n� de faltas injustificadas
     * si coinciden se tiene en cuenta las justificadas
     * M�todo de selecci�n directa
     */
    public void ordenar() {
      // Estudiante[] estudiantes = new Estudiante[total];
      // System.arraycopy(estudiantes, 0, total);
        // for (int i = 0; i < total - 1; i++){
            // int posmin = i;
            // for(int j = i + 1; j < total; j++){
                // if(estudiantes[j].getFaltasNoJustificadas().compareTo(estudiantes[posmin].getFaltasNoJustificadas())>=0){
                    // posmin = j;
                // }
            // }
            // int aux = estudiantes[posmin];
            // estudiantes[posmin] = estudiantes[i];
            // estudiantes[i] = aux;
        // }

    }

    /**
     * anular la matr�cula (dar de baja) a 
     * aquellos estudiantes con 30 o m�s faltas injustificadas
     */
    public void anularMatricula() {
         for(int i = 0; i < total; i++){
             if (estudiantes[i].getFaltasNoJustificadas() >= 30){
                 estudiantes[i - 1] = estudiantes[i];
             }
         }

    }

    /**
     * Lee de un fichero de texto los datos de los estudiantes
     *   con ayuda de un objeto de la  clase Scanner
     *   y los guarda en el array. 
     */
    public void leerDeFichero() {
        Scanner sc = null;
        try {
            sc = new Scanner(new File("estudiantes.txt"));
            while (sc.hasNextLine()) {
                String linea = sc.nextLine();
                Estudiante estudiante = new Estudiante(linea);
                this.addEstudiante(estudiante);

            }

        }
        catch (IOException e) {
            System.out.println("Error al leer del fichero");
        }
        finally {
            if (sc != null) {
                sc.close();
            }
        }

    }

}
