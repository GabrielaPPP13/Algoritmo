import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Collections;

public class Algoritmo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese la cantidad de cadenas a comparar:");
        int cantidadCadenas = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea

        ArrayList<String> cadenas = new ArrayList<>();

        System.out.println("Ingrese las cadenas:");

        // Leer las cadenas y agregarlas al ArrayList
        for (int i = 0; i < cantidadCadenas; i++) {
            String cadena = scanner.nextLine();
            cadenas.add(cadena);
        }

        int longitudMaxima = 0;
        for (String cadena : cadenas) {
            if (cadena.length() > longitudMaxima) {
                longitudMaxima = cadena.length();
            }
        }

        ArrayList<ArrayList<String>> resultados = new ArrayList<>();

        // Generar 10 resultados iniciales
        for (int iteracion = 0; iteracion < 10; iteracion++) {
            ArrayList<String> resultado = generarMatrizAleatoria(cadenas, longitudMaxima);

            // Calcular la calificación y agregar el resultado a la lista de resultados
            int calificacion = calcularCalificacion(resultado);
            resultado.add("Calificación: " + calificacion);
            resultados.add(resultado);
        }

        // Repetir el proceso durante 30 fases
        for (int fase = 0; fase < 30; fase++) {
            System.out.println("Fase " + (fase + 1));
            System.out.println("Los 5 mejores resultados antes del proceso adicional (Fase " + (fase + 1) + "): ");

            // Seleccionar y almacenar los 5 mejores resultados de la fase actual
            ArrayList<ArrayList<String>> resultadosActuales = new ArrayList<>(resultados.subList(0, 5));

            for (int i = 0; i < 5; i++) {
                System.out.println("Resultado " + (i + 1));
                for (String cadena : resultadosActuales.get(i)) {
                    System.out.println(cadena);
                }

                // Generar nuevos resultados duplicando y modificando el resultado actual
                ArrayList<String> resultadoActual = resultadosActuales.get(i);
                ArrayList<String> resultadoModificado = generarMatrizAleatoria(resultadoActual, longitudMaxima);

                int nuevaCalificacion = calcularCalificacion(resultadoModificado);

                // Eliminar la antigua calificación antes de agregar la nueva
                resultadoModificado.remove(resultadoModificado.size() - 1);
                resultadoModificado.add("Nueva Calificación: " + nuevaCalificacion);
                resultadosActuales.set(i, resultadoModificado);
            }

            // Mostrar los 5 mejores resultados después del proceso adicional
            System.out.println("Los 5 mejores resultados después del proceso adicional (Fase " + (fase + 1) + "): ");
            for (int i = 0; i < 5; i++) {
                System.out.println("Resultado " + (i + 1));
                for (String cadena : resultadosActuales.get(i)) {
                    System.out.println(cadena);
                }
            }

            // Reemplazar los resultados anteriores con los nuevos en la lista de resultados
            resultados.subList(0, 5).clear();
            resultados.addAll(resultadosActuales);
        }

        // Puedes continuar con la siguiente fase del algoritmo utilizando los resultados en "resultados".

        scanner.close();
    }

    public static ArrayList<String> generarMatrizAleatoria(ArrayList<String> cadenas, int longitudMaxima) {
        Random random = new Random();
        ArrayList<String> cadenas_modificadas = new ArrayList<>();
        boolean todasIguales = true;

        // Generar resultados hasta que no haya una columna de puros guiones
        while (todasIguales) {
            cadenas_modificadas.clear();
            todasIguales = true;

            for (String cadena : cadenas) {
                int posicion = random.nextInt(cadena.length());
                String cadenaModificada = cadena.substring(0, posicion) + "-" + cadena.substring(posicion);

                while (cadenaModificada.length() < longitudMaxima) {
                    cadenaModificada += "0";
                }
                cadenas_modificadas.add(cadenaModificada);

                if (!cadenaModificada.equals(cadenas_modificadas.get(0))) {
                    todasIguales = false;
                }
            }
        }

        return cadenas_modificadas;
    }

    public static int calcularCalificacion(ArrayList<String> resultado) {
        int totalCalificacion = 0;

        int longitudMaxima = resultado.get(0).length(); // Obtener la longitud máxima

        for (int columna = 0; columna < longitudMaxima; columna++) {
            int calificacionColumna = 0;
            for (String cadena : resultado) {
                if (columna < cadena.length()) { // Comprobar que columna sea válido
                    char letra = cadena.charAt(columna);

                    if (letra == '-') {
                        calificacionColumna--;
                    } else {
                        int repeticiones = contarRepeticiones(resultado, columna, letra);
                        calificacionColumna += repeticiones - 1;
                    }
                }
            }
            totalCalificacion += calificacionColumna;
        }

        return totalCalificacion;
    }

    public static int contarRepeticiones(ArrayList<String> resultado, int columna, char letra) {
        int repeticiones = 0;

        for (String cadena : resultado) {
            if (columna < cadena.length() && cadena.charAt(columna) == letra) {
                repeticiones++;
            }
        }

        return repeticiones;
    }
}
