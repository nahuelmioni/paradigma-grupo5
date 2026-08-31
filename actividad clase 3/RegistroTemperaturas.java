/**
 * Clase que gestiona el registro y análisis estadístico de las
 * temperaturas correspondientes a los días de una semana.
 */
public class RegistroTemperaturas {

    // Atributo encapsulado para proteger los datos
    private double[] temperaturasSemanales;

    // Constructor que inicializa el arreglo
    public RegistroTemperaturas() {
        temperaturasSemanales = new double[7];
    }

    // 1. Cargar las 7 temperaturas del arreglo
    public void cargarTemperaturas(double[] nuevasTemperaturas) {
        if (nuevasTemperaturas.length == temperaturasSemanales.length) {
            for (int i = 0; i < temperaturasSemanales.length; i++) {
                temperaturasSemanales[i] = nuevasTemperaturas[i];
            }
        }
    }

    // 2. Calcular e imprimir el promedio de la semana
    public void mostrarPromedioSemanal() {
        double sumaTemperaturas = 0.0;
        for (int i = 0; i < temperaturasSemanales.length; i++) {
            sumaTemperaturas += temperaturasSemanales[i];
        }

        double promedio = sumaTemperaturas / temperaturasSemanales.length;
        System.out.println("Promedio semanal: " + promedio + "°C");
    }

    // 3. Encontrar la temperatura máxima y la mínima
    public void mostrarExtremosTermicos() {
        double temperaturaMaxima = temperaturasSemanales[0];
        double temperaturaMinima = temperaturasSemanales[0];

        for (int i = 1; i < temperaturasSemanales.length; i++) {
            if (temperaturasSemanales[i] > temperaturaMaxima) {
                temperaturaMaxima = temperaturasSemanales[i];
            }
            if (temperaturasSemanales[i] < temperaturaMinima) {
                temperaturaMinima = temperaturasSemanales[i];
            }
        }
        System.out.println("Temperatura máxima: " + temperaturaMaxima + "°C");
        System.out.println("Temperatura mínima: " + temperaturaMinima + "°C");
    }

    // 4. Contar cuántos días superaron un umbral dado
    public void mostrarDiasSobreUmbral(double umbral) {
        int cantidadDias = 0;
        for (int i = 0; i < temperaturasSemanales.length; i++) {
            if (temperaturasSemanales[i] > umbral) {
                cantidadDias++;
            }
        }
        System.out.println("Días que superaron los " + umbral + "°C: " + cantidadDias);
    }

    // 5. Recorrer el arreglo e imprimir cada día con su temperatura
    public void imprimirReporteDiario() {
        for (int i = 0; i < temperaturasSemanales.length; i++) {
            System.out.println("Día " + (i + 1) + ": " + temperaturasSemanales[i] + "°C");
        }
    }
}
