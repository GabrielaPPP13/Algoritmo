import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Collections;
import java.util.Comparator;
//GABRIELA ABIGAIL PAULI CONTRERAS - 18014230
public class AlgoritmoDos {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese la cantidad de cadenas a comparar:");
        int cantidadCadenas = scanner.nextInt();
        scanner.nextLine();

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

        // Fase 1
        // Generar 10 resultados iniciales con el método de generarMatrizAleatoria
        for (int iteracion = 0; iteracion < 10; iteracion++) {
            ArrayList<String> resultado = generarMatrizAleatoria(cadenas, longitudMaxima);

            // Calcular la calificación y agregar el resultado a la lista de resultados
            int calificacion = calcularCalificacion(resultado);
            resultado.add("Calificación: " + calificacion);
            resultados.add(resultado);
        }

        // Ordenar las matrices de la Fase 1 por calificación de mayor a menor
        Collections.sort(resultados, new Comparator<ArrayList<String>>() {
            @Override
            public int compare(ArrayList<String> o1, ArrayList<String> o2) {
                int calificacion1 = obtenerCalificacion(o1);
                int calificacion2 = obtenerCalificacion(o2);
                return calificacion2 - calificacion1;
            }
        });

        // Mantén solo las 5 mejores matrices de la Fase 1
        resultados = new ArrayList<>(resultados.subList(0, 5));



        // Llama al método mutacionPorAfinidad para insertar guiones según la calificación en la Fase 1
        mutacionPorAfinidad(resultados);

        // Mostrar las matrices resultantes ordenadas por calificación de la Fase 1
        System.out.println("Fase 1 - Matrices finales ordenadas por calificación (de mayor a menor):");

        for (ArrayList<String> resultado : resultados) {
            for (String cadena : resultado) {
                System.out.println(cadena);
            }
        }

        // Duplica las matrices generadas en mutacionPorAfinidad en la Fase 1
       duplicarMatrices(resultados);

        eliminarColumnasDePurosGuiones(resultados);


        // Mostrar las matrices resultantes ordenadas por calificación de la Fase 1
     /*   System.out.println("Fase 1 - Matrices finales ordenadas por calificación (de mayor a menor):");

        for (ArrayList<String> resultado : resultados) {
            for (String cadena : resultado) {
                System.out.println(cadena);
            }
        }

        // Duplica las matrices generadas en mutacionPorAfinidad en la Fase 1
        duplicarMatrices(resultados);
*/



        // Ciclo para las Fases 2 a 10
        for (int fase = 2; fase <= 10; fase++) {
            System.out.println("\nFase " + fase + ":");

            // 1. Aplicar método de generarMatricesFase2 y asignar calificaciones
            generarMatricesFase2(resultados);

            // 2. Ordenar las matrices por calificación de mayor a menor
            ordenarMatricesPorCalificacion(resultados);

            // 3. Mostrar las 10 matrices generadas
            System.out.println("Matrices generadas:");

            for (ArrayList<String> resultado : resultados) {
                for (String cadena : resultado) {
                    System.out.println(cadena);
                }
            }

            // 4. Seleccionar 5 matrices
            ArrayList<ArrayList<String>> matricesSeleccionadas = new ArrayList<>(resultados.subList(0, 5));
            System.out.println("Matrices seleccionadas:");

            for (ArrayList<String> resultado : matricesSeleccionadas) {
                for (String cadena : resultado) {
                    System.out.println(cadena);
                }
            }

            // 5. Aplicar mutación por afinidad a las 5 matrices seleccionadas
            mutacionPorAfinidad(matricesSeleccionadas);

            // 6. Duplicar las 5 matrices seleccionadas
            duplicarMatrices(matricesSeleccionadas);

            // Actualizar las matrices para la próxima iteración
            resultados = new ArrayList<>(matricesSeleccionadas);

            eliminarColumnasDePurosGuiones(resultados);

        }

        scanner.close();
    }


    // Método para ordenar las matrices por calificación
    public static void ordenarMatricesPorCalificacion(ArrayList<ArrayList<String>> resultados) {
        Collections.sort(resultados, new Comparator<ArrayList<String>>() {
            @Override
            public int compare(ArrayList<String> o1, ArrayList<String> o2) {
                int calificacion1 = obtenerCalificacion(o1);
                int calificacion2 = obtenerCalificacion(o2);
                return calificacion2 - calificacion1; // Ordenar de mayor a menor
            }
        });
    }

    // Obtener la calificación de una matriz
    public static int obtenerCalificacion(ArrayList<String> resultado) {
        String calificacionLinea = resultado.get(resultado.size() - 1);
        String[] partes = calificacionLinea.split(": ");
        return Integer.parseInt(partes[1]);
    }




    public static void mutacionPorAfinidad(ArrayList<ArrayList<String>> resultados) {
        int longitudMaxima = resultados.get(0).get(0).length();

        for (int i = 0; i < resultados.size(); i++) {
            int guionesToAdd;
            if (longitudMaxima > 100) {
                guionesToAdd = (i + 1) * 10;
            } else {
                guionesToAdd = i + 1;
            }

            ArrayList<String> resultadoModificado = generarMatrizConGuiones(resultados.get(i), guionesToAdd);

            int nuevaCalificacion = calcularCalificacion(resultadoModificado);

            resultadoModificado.remove(resultadoModificado.size() - 1);
            resultadoModificado.add("Nueva Calificación: " + nuevaCalificacion);
            resultados.set(i, resultadoModificado);
        }
    }


    public static void duplicarMatrices(ArrayList<ArrayList<String>> resultados) {
        ArrayList<ArrayList<String>> nuevasMatrices = new ArrayList<>();
        for (ArrayList<String> resultado : resultados) {
            nuevasMatrices.add(new ArrayList<>(resultado));
        }
        resultados.addAll(nuevasMatrices);
    }

    public static void generarMatricesFase2(ArrayList<ArrayList<String>> resultados) {
        for (int i = 0; i < resultados.size(); i++) {
            ArrayList<String> resultadoModificado = generarMatrizConGuiones(resultados.get(i), 1);
            int nuevaCalificacion = calcularCalificacion(resultadoModificado);
            resultadoModificado.remove(resultadoModificado.size() - 1);
            resultadoModificado.add("Nueva Calificación: " + nuevaCalificacion);
            resultados.set(i, resultadoModificado);
        }
    }



    public static ArrayList<String> generarMatrizAleatoria(ArrayList<String> cadenas, int longitudMaxima) {
        Random random = new Random();
        ArrayList<String> cadenas_modificadas = new ArrayList<>();
        boolean todasIguales = true;

        while (todasIguales) {
            cadenas_modificadas.clear();
            todasIguales = true;

            for (String cadena : cadenas) {
                int posicion = random.nextInt(cadena.length());
                String cadenaModificada = cadena.substring(0, posicion) + "-" + cadena.substring(posicion);

                while (cadenaModificada.length() < longitudMaxima) {
                    cadenaModificada += "-";
                }
                cadenas_modificadas.add(cadenaModificada);

                if (!cadenaModificada.equals(cadenas_modificadas.get(0))) {
                    todasIguales = false;
                }
            }
        }

        return cadenas_modificadas;
    }



    public static ArrayList<String> generarMatrizConGuiones(ArrayList<String> resultado, int guionesToAdd) {
        ArrayList<String> nuevoResultado = new ArrayList<>();
        Random random = new Random();

        for (String cadena : resultado) {
            StringBuilder nuevaCadena = new StringBuilder(cadena);
            for (int i = 0; i < guionesToAdd; i++) {
                int posicion = random.nextInt(nuevaCadena.length() + 1); // aquí inserta en una posición aleatoria
                nuevaCadena.insert(posicion, "-");
            }
            nuevoResultado.add(nuevaCadena.toString());
        }

        return nuevoResultado;
    }



    public static int calcularCalificacion(ArrayList<String> resultado) {
        int totalCalificacion = 0;

        int longitudMaxima = resultado.get(0).length(); // Obtengo la longitud máxima

        for (int columna = 0; columna < longitudMaxima; columna++) {
            int calificacionColumna = 0;
            for (String cadena : resultado) {
                if (columna < cadena.length()) { // se tiene que comprobar que la columna sea valida
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


    // Función para eliminar las columnas de puros guiones de las matrices
    public static void eliminarColumnasDePurosGuiones(ArrayList<ArrayList<String>> resultados) {
        int longitudMaxima = resultados.get(0).get(0).length();
        ArrayList<Integer> columnasAEliminar = new ArrayList<>();

        for (int columna = 0; columna < longitudMaxima; columna++) {
            boolean columnaDeGuiones = true;
            for (ArrayList<String> resultado : resultados) {
                boolean columnaNoEsGuion = false;
                for (String cadena : resultado) {
                    if (columna < cadena.length() && cadena.charAt(columna) != '-') {
                        columnaNoEsGuion = true;
                        break;
                    }
                }
                if (columnaNoEsGuion) {
                    columnaDeGuiones = false;
                    break;
                }
            }
            if (columnaDeGuiones) {
                columnasAEliminar.add(columna);
            }
        }
        if (!columnasAEliminar.isEmpty()) {
            for (ArrayList<String> resultado : resultados) {
                for (int i = columnasAEliminar.size() - 1; i >= 0; i--) {
                    int columnaAEliminar = columnasAEliminar.get(i);
                    for (int j = 0; j < resultado.size(); j++) {
                        String cadena = resultado.get(j);
                        if (columnaAEliminar < cadena.length()) {
                            resultado.set(j, cadena.substring(0, columnaAEliminar) + cadena.substring(columnaAEliminar + 1));
                        }
                    }
                    longitudMaxima--;
                }
            }

            for (ArrayList<String> resultado : resultados) {
                for (int j = 0; j < resultado.size(); j++) {
                    String cadena = resultado.get(j);
                    if (cadena.length() > longitudMaxima) {
                        resultado.set(j, cadena.substring(0, longitudMaxima));
                    }
                }
            }
        }
    }





}
