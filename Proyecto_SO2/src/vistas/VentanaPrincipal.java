
package vistas;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import controladores.PlanificadorDisco;
import controladores.ControladorSistema;


public class VentanaPrincipal extends javax.swing.JFrame {
    private modelos.DiscoVirtual disco;
    private int ultimoIndiceColor = -1; // Nos ayuda a no repetir color
    // Este es el objeto que procesa los algoritmos (FIFO, SSTF, etc.)
    private PlanificadorDisco planificador = new PlanificadorDisco();
    private controladores.ControladorSistema controladorSistema;
    // Declarar las variables al inicio de la clase, junto con las otras variables
private javax.swing.JLabel lblCabezalInicial;
private javax.swing.JTextField txtCabezalInicial;
private javax.swing.JButton btnAplicarCabezal;
    
    
    
    // --- MÉTODO PARA ACTUALIZAR SOLO LA TABLA DE ARCHIVOS ---
    private void actualizarTablaArchivos(String nombre, int tamano, String metodo) {
        // Obtenemos el modelo de tu tabla secundaria (jTable2)
        javax.swing.table.DefaultTableModel modeloTabla = (javax.swing.table.DefaultTableModel) jTable2.getModel();
        
        // Si la tabla aún tiene las columnas por defecto de NetBeans, las acomodamos:
        if (modeloTabla.getColumnCount() == 4 && modeloTabla.getColumnName(0).equals("Title 1")) {
            String[] columnas = {"Nombre", "Tamaño", "Método", "Detalle de Bloques"};
            modeloTabla.setColumnIdentifiers(columnas);
            modeloTabla.setRowCount(0); // Limpiamos las filas vacías que trae por defecto
        }
        
        // Creamos una pequeña descripción dependiendo del método que eligió el usuario
        String bloquesTexto = "Automático"; 
        if (metodo.equals("Contigua")) {
            bloquesTexto = tamano + " bloques juntos";
        } else if (metodo.equals("Indexada")) {
            bloquesTexto = "1 Índice + " + tamano + " Datos";
        } else if (metodo.equals("Enlazada")) {
            bloquesTexto = tamano + " bloques dispersos";
        }

        // Agregamos la nueva fila con los 4 datos exactos
        modeloTabla.addRow(new Object[]{nombre, tamano + " blks", metodo, bloquesTexto});
    }
 
    public VentanaPrincipal() {
    initComponents();
    // Configurar campo para cabezal inicial
lblCabezalInicial = new javax.swing.JLabel("Cabezal Inicial:");
txtCabezalInicial = new javax.swing.JTextField("0", 5);
btnAplicarCabezal = new javax.swing.JButton("Aplicar");

btnAplicarCabezal.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        aplicarCabezalInicial();  // <- Aquí con C mayúscula
    }
});

// Agregar al panel (ajusta según tu layout)
jPanel1.add(lblCabezalInicial);
jPanel1.add(txtCabezalInicial);
jPanel1.add(btnAplicarCabezal);
    // Inicializamos el disco con 100 bloques
    disco = new modelos.DiscoVirtual(100);
    btnCargarPrueba = new javax.swing.JButton("Cargar Prueba");
btnCargarPrueba.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnCargarPruebaActionPerformed(evt);
    }
});
jPanel1.add(btnCargarPrueba);
    
    // --- INICIALIZAR EL CONTROLADOR DE SISTEMA ---
    estructuras.ListaEnlazada<modelos.Archivo> listaArchivosSistema = new estructuras.ListaEnlazada<>();
    estructuras.Cola<modelos.Proceso> colaProcesosSistema = new estructuras.Cola<>();
    controladorSistema = new controladores.ControladorSistema(planificador, listaArchivosSistema, colaProcesosSistema);
    
    actualizarMapaDisco();
    inicializarSistema();
    
    // --- ACTUALIZAR LAS LISTAS AL INICIAR ---
    actualizarListaJournaling();
    actualizarListaProcesos();
}
    
    private void inicializarSistema() {
        // 1. Configurar la Raíz del Árbol de Archivos
        DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("Disco Virtual");
        DefaultTreeModel modeloArbol = new DefaultTreeModel(raiz);
        arbolArchivos.setModel(modeloArbol);
        modeloArbol.setAsksAllowsChildren(true);
        // 2. Darle la bienvenida a la Consola Hacker
        txtConsola.setText("===================================\n");
        txtConsola.append("   SISTEMA DE ARCHIVOS INICIADO\n");
        txtConsola.append("===================================\n");
        txtConsola.append("> Disco montado exitosamente.\n");
        txtConsola.append("> Esperando comandos del usuario...\n\n");

        // 3. Centrar la ventana en la pantalla
        this.setLocationRelativeTo(null);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane6 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        comboUsuario = new javax.swing.JComboBox<>();
        comboPlanificador = new javax.swing.JComboBox<>();
        btnCrear = new javax.swing.JButton();
        btnLeer = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnSimularActionPerformed = new javax.swing.JButton();
        btnNuevaCarpeta = new javax.swing.JButton();
        btnCargarPrueba = new javax.swing.JButton();
        btnGuardarEstado = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        arbolArchivos = new javax.swing.JTree();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        listaProcesos = new javax.swing.JList<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        listaJournaling = new javax.swing.JList<>();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtConsola = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaBloqueos = new javax.swing.JTable();
        panelDisco = new javax.swing.JPanel();
        Peticiones = new javax.swing.JTabbedPane();
        jScrollPane7 = new javax.swing.JScrollPane();
        tablaAsignacionArchivos = new javax.swing.JTable();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        txtPeticiones = new javax.swing.JTextField();

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane6.setViewportView(jTable2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        comboUsuario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Administrador", "Usuario" }));
        comboUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboUsuarioActionPerformed(evt);
            }
        });

        comboPlanificador.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "FIFO", "SSTF", "SCAN", "C-SCAN" }));

        btnCrear.setText("Crear");
        btnCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearActionPerformed(evt);
            }
        });

        btnLeer.setText("Leer");
        btnLeer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLeerActionPerformed(evt);
            }
        });

        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnSimularActionPerformed.setText("Simular");
        btnSimularActionPerformed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimularActionPerformedActionPerformed(evt);
            }
        });

        btnNuevaCarpeta.setText("Nueva carpeta");
        btnNuevaCarpeta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaCarpetaActionPerformed(evt);
            }
        });

        btnCargarPrueba.setText("CargarP");
        btnCargarPrueba.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCargarPruebaActionPerformed(evt);
            }
        });

        btnGuardarEstado.setText("Guardar estado");
        btnGuardarEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarEstadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnCrear)
                        .addGap(18, 18, 18)
                        .addComponent(btnLeer)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnModificar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEliminar)
                        .addGap(18, 18, 18)
                        .addComponent(btnSimularActionPerformed)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNuevaCarpeta)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(comboUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCargarPrueba)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnGuardarEstado)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(comboPlanificador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comboPlanificador, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(comboUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCargarPrueba)
                        .addComponent(btnGuardarEstado)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCrear)
                    .addComponent(btnLeer)
                    .addComponent(btnModificar)
                    .addComponent(btnEliminar)
                    .addComponent(btnSimularActionPerformed)
                    .addComponent(btnNuevaCarpeta))
                .addContainerGap())
        );

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Disco Virtual");
        arbolArchivos.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jScrollPane1.setViewportView(arbolArchivos);

        jTabbedPane1.setToolTipText("");

        listaProcesos.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Cola vacia", "Sin procesos pendientes" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(listaProcesos);

        jTabbedPane1.addTab("Cola", jScrollPane2);

        listaJournaling.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Bitacora limpia" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane4.setViewportView(listaJournaling);

        jTabbedPane1.addTab("Journaling", jScrollPane4);

        txtConsola.setEditable(false);
        txtConsola.setBackground(new java.awt.Color(0, 0, 0));
        txtConsola.setColumns(20);
        txtConsola.setForeground(new java.awt.Color(0, 255, 0));
        txtConsola.setRows(5);
        jScrollPane5.setViewportView(txtConsola);

        jTabbedPane1.addTab("Consola", jScrollPane5);

        tablaBloqueos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Archivo", "Estado del Lock", "Lectores Activos"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tablaBloqueos);

        jTabbedPane1.addTab("Bloqueos", jScrollPane3);

        javax.swing.GroupLayout panelDiscoLayout = new javax.swing.GroupLayout(panelDisco);
        panelDisco.setLayout(panelDiscoLayout);
        panelDiscoLayout.setHorizontalGroup(
            panelDiscoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelDiscoLayout.setVerticalGroup(
            panelDiscoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 260, Short.MAX_VALUE)
        );

        tablaAsignacionArchivos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Nombre del archivo", "Cantidad de Bloques", "Dirección Inicial", "Direccion final", "Color"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Object.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane7.setViewportView(tablaAsignacionArchivos);

        Peticiones.addTab("Archivo", jScrollPane7);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Paso", "Bloque Visitado", "Distancia (Costo)"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane8.setViewportView(jTable1);

        Peticiones.addTab("Planificador", jScrollPane8);

        txtPeticiones.setText("\"Bloques a visitar (ej: 5,10,20):\"");
        txtPeticiones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPeticionesActionPerformed(evt);
            }
        });
        Peticiones.addTab("Peticiones", txtPeticiones);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelDisco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Peticiones, javax.swing.GroupLayout.DEFAULT_SIZE, 618, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 559, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panelDisco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Peticiones, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(16, 16, 16))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void comboUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboUsuarioActionPerformed

    private void btnLeerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLeerActionPerformed
        // 1. Verificar si el usuario seleccionó una fila
    int filaSeleccionada = tablaAsignacionArchivos.getSelectedRow();
    
    if (filaSeleccionada == -1) {
        javax.swing.JOptionPane.showMessageDialog(this, 
            "Por favor, seleccione un archivo de la tabla central para leer.", 
            "Ningún archivo seleccionado", 
            javax.swing.JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    // 2. Obtener los datos del archivo desde la tabla
    String nombreActual = tablaAsignacionArchivos.getValueAt(filaSeleccionada, 0).toString();
    String bloquesActuales = tablaAsignacionArchivos.getValueAt(filaSeleccionada, 1).toString();
    String direccionActual = tablaAsignacionArchivos.getValueAt(filaSeleccionada, 2).toString();
    String colorActual = tablaAsignacionArchivos.getValueAt(filaSeleccionada, 3).toString();
    
    // 3. Obtener la cantidad de bloques como entero
    int cantidadBloques = Integer.parseInt(bloquesActuales.split(" ")[0]);
    int direccionInicial = Integer.parseInt(direccionActual);
    
    // --- 4. AGREGAR PROCESO DE LECTURA A LA COLA ---
    agregarProcesoACola("LEER", nombreActual, cantidadBloques, direccionInicial);
    
    // 5. Simular que se está leyendo (Avisar en consola)
    String rolActual = comboUsuario.getSelectedItem().toString();
    txtConsola.append("> [LECTURA] " + rolActual.toUpperCase() + " está leyendo el archivo '" + nombreActual + "'...\n");
    
    // 6. Cambiar el estado del proceso a EJECUTANDO (simulación)
    // Buscar el último proceso agregado y cambiar su estado
    if (!controladorSistema.getColaProcesos().estaVacia()) {
        estructuras.Nodo<modelos.Proceso> actual = controladorSistema.getColaProcesos().getFrente();
        while (actual.getSiguiente() != null) {
            actual = actual.getSiguiente();
        }
        actual.getDato().setEstado("EJECUTANDO");
        actualizarListaProcesos();
    }
    
    // 7. Mostrar las "Propiedades" del archivo
    String mensajeLectura = "=== PROPIEDADES DEL ARCHIVO ===\n\n" +
                            "Nombre: " + nombreActual + "\n" +
                            "Tamaño: " + bloquesActuales + "\n" +
                            "Dirección en Disco: " + direccionActual + "\n" +
                            "Color Asignado: " + colorActual + "\n\n" +
                            "Estado: Lectura finalizada con éxito.";
                            
    javax.swing.JOptionPane.showMessageDialog(this, 
            mensajeLectura, 
            "Leyendo: " + nombreActual, 
            javax.swing.JOptionPane.INFORMATION_MESSAGE);
    
    // 8. Cambiar el estado del proceso a TERMINADO
    if (!controladorSistema.getColaProcesos().estaVacia()) {
        estructuras.Nodo<modelos.Proceso> actual = controladorSistema.getColaProcesos().getFrente();
        while (actual.getSiguiente() != null) {
            actual = actual.getSiguiente();
        }
        actual.getDato().setEstado("TERMINADO");
        actualizarListaProcesos();
    }
    
    // 9. Avisar que se terminó de leer
    txtConsola.append("> [OK] Lectura de '" + nombreActual + "' finalizada.\n");     // TODO add your handling code here:
    }//GEN-LAST:event_btnLeerActionPerformed

    private void btnCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearActionPerformed
    // --- 1. VALIDACIÓN DE PERMISOS ---
    String rolActual = comboUsuario.getSelectedItem().toString();
    if (!rolActual.equalsIgnoreCase("Administrador")) {
        javax.swing.JOptionPane.showMessageDialog(this, 
            "Acceso Denegado: Solo los Administradores pueden crear archivos.", 
            "Modo Usuario (Solo Lectura)", 
            javax.swing.JOptionPane.ERROR_MESSAGE);
        txtConsola.append("> [ALERTA] Intento de creación bloqueado.\n");
        return; 
    }

    // --- 2. PEDIR NOMBRE ---
    String nombreArchivo = javax.swing.JOptionPane.showInputDialog(this, 
            "Ingrese el nombre del archivo:", "Crear Archivo", javax.swing.JOptionPane.QUESTION_MESSAGE);
    
    if (nombreArchivo == null || nombreArchivo.trim().isEmpty()) return;

    // --- 3. PEDIR TAMAÑO EN BLOQUES ---
    String bloquesStr = javax.swing.JOptionPane.showInputDialog(this, 
            "Cantidad de bloques para '" + nombreArchivo + "':", "Tamaño", javax.swing.JOptionPane.QUESTION_MESSAGE);
    
    if (bloquesStr == null || bloquesStr.trim().isEmpty()) return;

    try {
        int cantidadBloques = Integer.parseInt(bloquesStr);
        
        // --- 4. REGISTRAR INICIO EN JOURNAL (ANTES DE MODIFICAR EL DISCO) ---
        modelos.RegistroJournal registroJournal = controladorSistema.registrarInicioOperacion("CREAR", nombreArchivo, cantidadBloques);
        actualizarListaJournaling(); // Refrescar la vista del journal
        txtConsola.append("> 📝 [JOURNAL] Operación CREAR registrada como PENDIENTE: " + nombreArchivo + "\n");
        
        // --- 5. PALETA DE COLORES ---
        java.awt.Color[] paletaColores = {
            new java.awt.Color(255, 80, 80),   // Rojo
            new java.awt.Color(80, 150, 255),  // Azul
            new java.awt.Color(100, 220, 100), // Verde
            new java.awt.Color(255, 200, 50),  // Naranja
            new java.awt.Color(180, 100, 255), // Morado
            new java.awt.Color(255, 120, 200), // Rosa
            new java.awt.Color(100, 255, 255)  // Celeste
        };
        String[] nombresColores = {"Rojo", "Azul", "Verde", "Naranja", "Morado", "Rosa", "Celeste"};
        
        int nuevoIndice;
        do {
            nuevoIndice = (int) (Math.random() * paletaColores.length);
        } while (nuevoIndice == ultimoIndiceColor);
        
        ultimoIndiceColor = nuevoIndice;
        
        java.awt.Color colorNuevo = paletaColores[nuevoIndice];
        String nombreColor = nombresColores[nuevoIndice];

        // --- 6. ASIGNAR ESPACIO EN EL DISCO ---
        int direccionInicial = disco.asignarEspacio(cantidadBloques, colorNuevo);
        
        if (direccionInicial == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "No hay espacio suficiente.");
            return; 
        }
        
        // Calcular bloque final
        int bloqueFinal = direccionInicial;
        int[] tablaAsignacion = disco.getTablaAsignacion();
        while (tablaAsignacion[bloqueFinal] != -1) {
            bloqueFinal = tablaAsignacion[bloqueFinal];
        }

        // --- 7. INSERTAR EN LA TABLA DE ARCHIVOS ---
        javax.swing.table.DefaultTableModel modeloTabla = (javax.swing.table.DefaultTableModel) tablaAsignacionArchivos.getModel();
        modeloTabla.addRow(new Object[]{
            nombreArchivo, 
            cantidadBloques, 
            direccionInicial, 
            bloqueFinal, 
            nombreColor
        });

        // --- 8. AGREGAR AL ÁRBOL (JTree) ---
        crearArchivoEnArbol(nombreArchivo);
        
        // --- 9. ACTUALIZAR MAPA DEL DISCO ---
        actualizarMapaDisco();
        
        // --- 10. CONFIRMAR OPERACIÓN EN JOURNAL (DESPUÉS DE MODIFICAR EL DISCO) ---
        controladorSistema.confirmarOperacion(registroJournal);
        actualizarListaJournaling(); // Refrescar la vista del journal
        txtConsola.append("> ✅ [JOURNAL] Operación CREAR CONFIRMADA: " + nombreArchivo + "\n");
        
        agregarProcesoACola("CREAR", nombreArchivo, cantidadBloques, direccionInicial);

// --- REGISTRAR EN CONSOLA ---
txtConsola.append("> [SISTEMA] Archivo '" + nombreArchivo + "' creado con color " + nombreColor + ".\n");
        
        // --- 11. REGISTRAR EN CONSOLA ---
        txtConsola.append("> [SISTEMA] Archivo '" + nombreArchivo + "' creado con color " + nombreColor + ".\n");

    } catch (NumberFormatException e) {
        javax.swing.JOptionPane.showMessageDialog(this, "Error: La cantidad de bloques debe ser un número entero.");
    }
    
    }//GEN-LAST:event_btnCrearActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
    // --- 1. VALIDACIÓN DE PERMISOS ---
    String rolActual = comboUsuario.getSelectedItem().toString();
    if (!rolActual.equalsIgnoreCase("Administrador")) {
        javax.swing.JOptionPane.showMessageDialog(this, 
            "Acceso Denegado: Solo los Administradores pueden eliminar archivos.", 
            "Modo Usuario (Solo Lectura)", 
            javax.swing.JOptionPane.ERROR_MESSAGE);
        txtConsola.append("> [ALERTA] Intento de eliminación bloqueado por falta de permisos.\n");
        return; 
    }

    // --- 2. VERIFICAR SELECCIÓN EN LA TABLA ---
    int filaSeleccionada = tablaAsignacionArchivos.getSelectedRow();
    if (filaSeleccionada == -1) {
        javax.swing.JOptionPane.showMessageDialog(this, 
            "Por favor, seleccione un archivo de la tabla central para eliminar.", 
            "Ningún archivo seleccionado", 
            javax.swing.JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    // --- 3. OBTENER DATOS DE LA TABLA ---
    String nombreArchivo = tablaAsignacionArchivos.getValueAt(filaSeleccionada, 0).toString();
    int direccionInicial = Integer.parseInt(tablaAsignacionArchivos.getValueAt(filaSeleccionada, 2).toString());
    
    // --- 4. PEDIR CONFIRMACIÓN ---
    int confirmacion = javax.swing.JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de que desea eliminar el archivo '" + nombreArchivo + "' y liberar sus bloques?", 
            "Confirmar Eliminación", 
            javax.swing.JOptionPane.YES_NO_OPTION,
            javax.swing.JOptionPane.QUESTION_MESSAGE);
            
    if (confirmacion != javax.swing.JOptionPane.YES_OPTION) {
        return;
    }
    
    // --- 5. REGISTRAR INICIO EN JOURNAL (ANTES DE MODIFICAR EL DISCO) ---
    modelos.RegistroJournal registroJournal = controladorSistema.registrarInicioOperacion("ELIMINAR", nombreArchivo, 0);
    actualizarListaJournaling();
    txtConsola.append("> 📝 [JOURNAL] Operación ELIMINAR registrada como PENDIENTE: " + nombreArchivo + "\n");
    
    agregarProcesoACola("ELIMINAR", nombreArchivo, 0, direccionInicial);
    // --- 6. LIBERAR ESPACIO EN EL DISCO ---
    disco.liberarEspacio(direccionInicial);
    actualizarMapaDisco();
    
    // --- 7. ELIMINAR DE LA TABLA ---
    javax.swing.table.DefaultTableModel modeloTabla = (javax.swing.table.DefaultTableModel) tablaAsignacionArchivos.getModel();
    modeloTabla.removeRow(filaSeleccionada);
    
    // --- 8. ELIMINAR DEL ÁRBOL (JTREE) ---
    javax.swing.tree.DefaultTreeModel modeloArbol = (javax.swing.tree.DefaultTreeModel) arbolArchivos.getModel();
    javax.swing.tree.DefaultMutableTreeNode raiz = (javax.swing.tree.DefaultMutableTreeNode) modeloArbol.getRoot();
    
    for (int i = 0; i < raiz.getChildCount(); i++) {
        javax.swing.tree.DefaultMutableTreeNode nodo = (javax.swing.tree.DefaultMutableTreeNode) raiz.getChildAt(i);
        if (nodo.getUserObject().toString().startsWith(nombreArchivo + " [") || nodo.getUserObject().toString().equals(nombreArchivo)) {
            modeloArbol.removeNodeFromParent(nodo); 
            break; 
        }
    }
    
    // --- 9. CONFIRMAR OPERACIÓN EN JOURNAL (DESPUÉS DE MODIFICAR EL DISCO) ---
    controladorSistema.confirmarOperacion(registroJournal);
    actualizarListaJournaling();
    txtConsola.append("> ✅ [JOURNAL] Operación ELIMINAR CONFIRMADA: " + nombreArchivo + "\n");
    
    // --- 10. REGISTRAR EN CONSOLA ---
    txtConsola.append("> [ELIMINADO] " + rolActual.toUpperCase() + " eliminó '" + nombreArchivo + "' y se liberaron sus bloques.\n");      // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
   // --- VALIDACIÓN DE PERMISOS ---
    String rolActual = comboUsuario.getSelectedItem().toString();
    if (!rolActual.equalsIgnoreCase("Administrador")) {
        javax.swing.JOptionPane.showMessageDialog(this, 
            "Acceso Denegado: Solo los Administradores pueden modificar archivos.", 
            "Modo Usuario (Solo Lectura)", 
            javax.swing.JOptionPane.ERROR_MESSAGE);
        txtConsola.append("> [ALERTA] Intento de modificación bloqueado por falta de permisos.\n");
        return; 
    }

    // 1. Verificar si el usuario seleccionó una fila
    int filaSeleccionada = tablaAsignacionArchivos.getSelectedRow();
    
    if (filaSeleccionada == -1) {
        javax.swing.JOptionPane.showMessageDialog(this, 
            "Por favor, seleccione un archivo de la tabla central para modificar.", 
            "Ningún archivo seleccionado", 
            javax.swing.JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    // 2. Obtener los datos actuales de la tabla
    String nombreActual = tablaAsignacionArchivos.getValueAt(filaSeleccionada, 0).toString();
    int cantidadBloquesActual = Integer.parseInt(tablaAsignacionArchivos.getValueAt(filaSeleccionada, 1).toString());
    int direccionInicialActual = Integer.parseInt(tablaAsignacionArchivos.getValueAt(filaSeleccionada, 2).toString());
    String nombreColor = tablaAsignacionArchivos.getValueAt(filaSeleccionada, 4).toString();
    
    // 3. Obtener el color real del archivo desde el disco
    java.awt.Color colorArchivo = disco.getColorBloques()[direccionInicialActual];
    
    // 4. Pedir el nuevo nombre
    String nuevoNombre = javax.swing.JOptionPane.showInputDialog(this, 
            "Modificar nombre del archivo:", nombreActual);
            
    if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) {
        return; 
    }
    
    // 5. Pedir la nueva cantidad de bloques
    String nuevosBloquesStr = javax.swing.JOptionPane.showInputDialog(this, 
            "Modificar cantidad de bloques (actual: " + cantidadBloquesActual + "):", 
            cantidadBloquesActual);
            
    if (nuevosBloquesStr == null || nuevosBloquesStr.trim().isEmpty()) {
        return;
    }
    
    try {
        int nuevosBloques = Integer.parseInt(nuevosBloquesStr);
        
        // --- 6. REGISTRAR INICIO EN JOURNAL ---
        if (controladorSistema != null) {
            modelos.RegistroJournal registroJournal = controladorSistema.registrarInicioOperacion("MODIFICAR", nombreActual, nuevosBloques);
            actualizarListaJournaling();
            txtConsola.append("> 📝 [JOURNAL] Operación MODIFICAR registrada como PENDIENTE: " + nombreActual + "\n");
        }
        
        // --- 7. LIBERAR LOS BLOQUES ANTIGUOS ---
        txtConsola.append("> [MODIFICAR] Liberando " + cantidadBloquesActual + " bloques antiguos de '" + nombreActual + "'...\n");
        disco.liberarEspacio(direccionInicialActual);
        
        // --- 8. ASIGNAR NUEVOS BLOQUES CON EL MISMO COLOR ---
        int nuevaDireccionInicial = disco.asignarEspacio(nuevosBloques, colorArchivo);
        
        if (nuevaDireccionInicial == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, 
                "No hay espacio suficiente para el nuevo tamaño (" + nuevosBloques + " bloques).\n" +
                "Los bloques antiguos ya fueron liberados.", 
                "Error de Espacio", 
                javax.swing.JOptionPane.ERROR_MESSAGE);
            txtConsola.append("> [ERROR] No hay espacio para " + nuevosBloques + " bloques.\n");
            
            // Eliminar la fila de la tabla porque el archivo ya no existe
            javax.swing.table.DefaultTableModel modeloTabla = (javax.swing.table.DefaultTableModel) tablaAsignacionArchivos.getModel();
            modeloTabla.removeRow(filaSeleccionada);
            
            // Eliminar del árbol
            eliminarArchivoDelArbol(nombreActual);
            
            actualizarMapaDisco();
            return;
        }
        
        // Calcular bloque final
        int bloqueFinal = nuevaDireccionInicial;
        int[] tablaAsignacion = disco.getTablaAsignacion();
        while (tablaAsignacion[bloqueFinal] != -1) {
            bloqueFinal = tablaAsignacion[bloqueFinal];
        }
        
        // --- 9. ACTUALIZAR LA TABLA ---
        javax.swing.table.DefaultTableModel modeloTabla = (javax.swing.table.DefaultTableModel) tablaAsignacionArchivos.getModel();
        modeloTabla.setValueAt(nuevoNombre, filaSeleccionada, 0);
        modeloTabla.setValueAt(nuevosBloques, filaSeleccionada, 1);
        modeloTabla.setValueAt(nuevaDireccionInicial, filaSeleccionada, 2);
        modeloTabla.setValueAt(bloqueFinal, filaSeleccionada, 3);
        // El color se mantiene igual (columna 4)
        
        // --- 10. ACTUALIZAR EL ÁRBOL (JTREE) ---
        actualizarArchivoEnArbol(nombreActual, nuevoNombre);
        
        // --- 11. ACTUALIZAR MAPA DEL DISCO ---
        actualizarMapaDisco();
        
        // --- 12. AGREGAR PROCESO A LA COLA ---
        agregarProcesoACola("MODIFICAR", nuevoNombre, nuevosBloques, nuevaDireccionInicial);
        
        // --- 13. CONFIRMAR OPERACIÓN EN JOURNAL ---
        if (controladorSistema != null) {
            // Buscar el último registro PENDIENTE para confirmarlo
            for (int i = controladorSistema.getBitacora().getTamaño() - 1; i >= 0; i--) {
                modelos.RegistroJournal reg = controladorSistema.getBitacora().obtener(i);
                if (reg.getEstado().equals("PENDIENTE") && reg.getNombreArchivo().equals(nombreActual)) {
                    controladorSistema.confirmarOperacion(reg);
                    break;
                }
            }
            actualizarListaJournaling();
            txtConsola.append("> ✅ [JOURNAL] Operación MODIFICAR CONFIRMADA: " + nombreActual + " → " + nuevoNombre + "\n");
        }
        
        // --- 14. CONSOLA ---
        txtConsola.append("> [MODIFICADO] " + rolActual.toUpperCase() + " modificó '" + nombreActual + "' → '" + nuevoNombre + "'\n");
        txtConsola.append(">   Tamaño: " + cantidadBloquesActual + " bloques → " + nuevosBloques + " bloques\n");
        txtConsola.append(">   Nueva dirección inicial: " + nuevaDireccionInicial + "\n");
        txtConsola.append(">   Nueva dirección final: " + bloqueFinal + "\n");
        
        javax.swing.JOptionPane.showMessageDialog(this, 
            "Archivo modificado exitosamente:\n" +
            "Nombre: " + nuevoNombre + "\n" +
            "Tamaño: " + nuevosBloques + " bloques\n" +
            "Dirección inicial: " + nuevaDireccionInicial + "\n" +
            "Dirección final: " + bloqueFinal + "\n" +
            "Color: " + nombreColor,
            "Modificación Exitosa",
            javax.swing.JOptionPane.INFORMATION_MESSAGE);
        
    } catch (NumberFormatException e) {
        javax.swing.JOptionPane.showMessageDialog(this, 
                "Error: La cantidad de bloques debe ser un número entero.", 
                "Error de entrada", 
                javax.swing.JOptionPane.ERROR_MESSAGE);
    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this, 
                "Error al modificar: " + e.getMessage(), 
                "Error", 
                javax.swing.JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnSimularActionPerformedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimularActionPerformedActionPerformed
// --- OBTENER LA POLÍTICA SELECCIONADA ---
    String algoritmo = comboPlanificador.getSelectedItem().toString();
    
    // --- CREAR PANEL PARA CONFIGURACIÓN ---
    javax.swing.JPanel panelConfiguracion = new javax.swing.JPanel();
    panelConfiguracion.setLayout(new java.awt.GridLayout(0, 2, 10, 10));
    
    // Campo para cabezal inicial
    String cabezalActual = String.valueOf(planificador.getPosicionCabezal());
    javax.swing.JLabel lblCabezal = new javax.swing.JLabel("Posición del cabezal:");
    javax.swing.JTextField txtCabezal = new javax.swing.JTextField(cabezalActual, 5);
    javax.swing.JCheckBox chkUsarCabezalActual = new javax.swing.JCheckBox("Usar posición actual (" + cabezalActual + ")", true);
    
    panelConfiguracion.add(lblCabezal);
    panelConfiguracion.add(txtCabezal);
    panelConfiguracion.add(new javax.swing.JLabel());
    panelConfiguracion.add(chkUsarCabezalActual);
    
    // --- CAMPO PARA DIRECCIÓN (SOLO PARA SCAN Y C-SCAN) ---
    javax.swing.JLabel lblDireccion = new javax.swing.JLabel("Dirección:");
    javax.swing.JComboBox<String> cmbDireccion = new javax.swing.JComboBox<>(new String[]{"↑ Hacia arriba (ASCENDENTE)", "↓ Hacia abajo (DESCENDENTE)"});
    
    // Solo mostrar dirección si el algoritmo es SCAN o C-SCAN
    if (algoritmo.equals("SCAN") || algoritmo.equals("C-SCAN")) {
        panelConfiguracion.add(lblDireccion);
        panelConfiguracion.add(cmbDireccion);
        
        // Establecer dirección por defecto según el algoritmo
        if (algoritmo.equals("SCAN")) {
            cmbDireccion.setSelectedIndex(0); // Arriba por defecto
        } else if (algoritmo.equals("C-SCAN")) {
            cmbDireccion.setSelectedIndex(0); // Arriba por defecto
        }
    }
    
    // Mostrar el diálogo
    int opcion = javax.swing.JOptionPane.showConfirmDialog(this, 
        panelConfiguracion, 
        "Configurar Simulación - " + algoritmo, 
        javax.swing.JOptionPane.OK_CANCEL_OPTION,
        javax.swing.JOptionPane.QUESTION_MESSAGE);
    
    if (opcion != javax.swing.JOptionPane.OK_OPTION) {
        txtConsola.append("> [SIMULACIÓN] Cancelada por el usuario.\n");
        return;
    }
    
    // --- PROCESAR CABEZAL INICIAL ---
    int posicionCabezalUsar = planificador.getPosicionCabezal();
    
    if (chkUsarCabezalActual.isSelected()) {
        posicionCabezalUsar = planificador.getPosicionCabezal();
        txtConsola.append("> [CABEZAL] Usando posición actual: " + posicionCabezalUsar + "\n");
    } else {
        try {
            int nuevaPosicion = Integer.parseInt(txtCabezal.getText().trim());
            if (nuevaPosicion >= 0 && nuevaPosicion < disco.getCapacidadTotal()) {
                posicionCabezalUsar = nuevaPosicion;
                planificador.setPosicionCabezal(nuevaPosicion);
                txtConsola.append("> [CABEZAL] Posición cambiada a: " + nuevaPosicion + "\n");
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, 
                    "Posición inválida. Usando posición actual: " + posicionCabezalUsar,
                    "Advertencia",
                    javax.swing.JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this, 
                "Valor inválido. Usando posición actual: " + posicionCabezalUsar,
                "Advertencia",
                javax.swing.JOptionPane.WARNING_MESSAGE);
        }
    }
    
    // --- PROCESAR DIRECCIÓN (PARA SCAN Y C-SCAN) ---
    if (algoritmo.equals("SCAN") || algoritmo.equals("C-SCAN")) {
        boolean direccionArriba = cmbDireccion.getSelectedIndex() == 0;
        
        // Configurar la dirección en el planificador
        planificador.setDireccion(direccionArriba);
        
        String direccionTexto = direccionArriba ? "ASCENDENTE (hacia arriba)" : "DESCENDENTE (hacia abajo)";
        txtConsola.append("> [DIRECCIÓN] Configurada: " + direccionTexto + "\n");
    }
    
    planificador.setPosicionCabezal(posicionCabezalUsar);
    
    // Cambiar a la pestaña Planificador
    Peticiones.setSelectedIndex(1);
    
    try {
        // 1. Limpiar la tabla de simulaciones anteriores
        javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) jTable1.getModel();
        
        if (modelo.getColumnCount() == 0) {
            String[] columnas = {"Paso", "Bloque Visitado", "Distancia (Costo)"};
            modelo.setColumnIdentifiers(columnas);
        }
        modelo.setRowCount(0);
        
        // 2. Obtener y validar las peticiones
        String texto = txtPeticiones.getText().trim();
        if (texto.isEmpty() || texto.equals("\"Bloques a visitar (ej: 5,10,20):\"")) {
            javax.swing.JOptionPane.showMessageDialog(this, "Escribe bloques: ej. 10,20,5");
            return;
        }

        // 3. Configurar la política (Algoritmo)
        planificador.setPolitica(algoritmo);
        
        // Limpiar la memoria del planificador
        planificador.limpiarCola();
        
        txtConsola.append("> [PLANIFICADOR] Iniciando simulación desde bloque: " + posicionCabezalUsar + "\n");
        
        // Limpiamos la cuadrícula visual
        actualizarMapaDisco(); 
        
        // 4. Cargar los números en el planificador
        String[] bloques = texto.split(",");
        for (String b : bloques) {
            planificador.agregarSolicitud(Integer.parseInt(b.trim()));
        }

        // 5. Mensaje inicial en consola
        txtConsola.append("> [PLANIFICADOR] Iniciando algoritmo " + algoritmo + "...\n");

        // 6. Hilo exclusivo para la animación visual
        new Thread(() -> {
            try {
                int posAnterior = planificador.getPosicionCabezal();
                int costoTotal = 0; 
                int numSolicitudes = bloques.length;
                
                for (int i = 0; i < numSolicitudes; i++) {
                    Thread.sleep(1000);
                    
                    int posActual = planificador.procesarSiguiente(); 
                    int costo = Math.abs(posActual - posAnterior);
                    costoTotal += costo; 
                    
                    final int paso = i + 1;
                    final int bloqueActual = posActual;
                    final int costoMov = costo;

                    java.awt.EventQueue.invokeLater(() -> {
                        modelo.addRow(new Object[]{paso, bloqueActual, costoMov + " blks"});
                        txtConsola.append("> Paso " + paso + ": Cabezal en bloque " + bloqueActual + " (Costo: " + costoMov + ")\n");
                        txtConsola.setCaretPosition(txtConsola.getDocument().getLength());
                        colorearBloque(bloqueActual, java.awt.Color.RED);
                    });
                    
                    posAnterior = posActual;
                }
                
                final int totalFinal = costoTotal;
                java.awt.EventQueue.invokeLater(() -> {
                    txtConsola.append("> [OK] Simulación finalizada.\n");
                    txtConsola.append("> 💰 COSTO TOTAL DEL VIAJE: " + totalFinal + " bloques.\n");
                    txtConsola.append("----------------------------------------------------\n");
                    txtConsola.setCaretPosition(txtConsola.getDocument().getLength());
                });

                Thread.sleep(1500);
                java.awt.EventQueue.invokeLater(() -> actualizarMapaDisco());

            } catch (Exception e) {
                System.out.println("Error en hilo de animación: " + e.getMessage());
            }
        }).start();

    } catch (NumberFormatException e) {
        javax.swing.JOptionPane.showMessageDialog(this, "Error: Ingresa solo números enteros separados por comas.");
    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this, "Error técnico: " + e.getMessage());
    }
    }//GEN-LAST:event_btnSimularActionPerformedActionPerformed

    private void txtPeticionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPeticionesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPeticionesActionPerformed

    private void btnNuevaCarpetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaCarpetaActionPerformed
   // 1. Validar permisos (solo administradores pueden crear carpetas)
    String rolActual = comboUsuario.getSelectedItem().toString();
    if (!rolActual.equalsIgnoreCase("Administrador")) {
        javax.swing.JOptionPane.showMessageDialog(this, 
            "Acceso Denegado: Solo los Administradores pueden crear carpetas.", 
            "Modo Usuario (Solo Lectura)", 
            javax.swing.JOptionPane.ERROR_MESSAGE);
        txtConsola.append("> [ALERTA] Intento de creación de carpeta bloqueado.\n");
        return;
    }
    
    // 2. Pedir el nombre de la carpeta
    String nombreCarpeta = javax.swing.JOptionPane.showInputDialog(
            this, 
            "Ingresa el nombre de la nueva carpeta:", 
            "Crear Directorio", 
            javax.swing.JOptionPane.PLAIN_MESSAGE
    );
    
    // 3. Verificar que no sea cancelado o vacío
    if (nombreCarpeta != null && !nombreCarpeta.trim().isEmpty()) {
        
        // 4. Crear la carpeta en el árbol
        crearNuevaCarpeta(nombreCarpeta.trim());
        
        // 5. Registrar en consola
        txtConsola.append("> [SISTEMA] Directorio creado: '" + nombreCarpeta.trim() + "'.\n");
        
    } else if (nombreCarpeta != null) {
        javax.swing.JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío.", "Error", javax.swing.JOptionPane.WARNING_MESSAGE);
    }
    }//GEN-LAST:event_btnNuevaCarpetaActionPerformed

    private void btnCargarPruebaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargarPruebaActionPerformed
        // 1. Verificar permisos (solo administrador puede cargar pruebas)
    String rolActual = comboUsuario.getSelectedItem().toString();
    if (!rolActual.equalsIgnoreCase("Administrador")) {
        javax.swing.JOptionPane.showMessageDialog(this, 
            "Acceso Denegado: Solo los Administradores pueden cargar casos de prueba.", 
            "Permiso Denegado", 
            javax.swing.JOptionPane.ERROR_MESSAGE);
        txtConsola.append("> [ALERTA] Intento de carga de prueba bloqueado.\n");
        return;
    }
    
    // 2. Seleccionar archivo JSON
    javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
    fileChooser.setDialogTitle("Seleccionar archivo de caso de prueba");
    fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Archivos JSON", "json"));
    
    int resultado = fileChooser.showOpenDialog(this);
    
    if (resultado == javax.swing.JFileChooser.APPROVE_OPTION) {
        String rutaArchivo = fileChooser.getSelectedFile().getAbsolutePath();
        txtConsola.append("> [PRUEBA] Cargando caso de prueba desde: " + rutaArchivo + "\n");
        
        // 3. Cargar el caso de prueba usando el controlador
        boolean exito = controladorSistema.cargarCasoDePrueba(rutaArchivo);
        
        if (exito) {
            txtConsola.append("> [PRUEBA] ✅ Caso de prueba cargado exitosamente.\n");
            
            // 4. Actualizar la interfaz con los datos cargados
            actualizarInterfazDesdeControlador();
            
            javax.swing.JOptionPane.showMessageDialog(this, 
                "Caso de prueba cargado exitosamente.\n" +
                "Revise la cola de procesos y la tabla de archivos.",
                "Carga Exitosa",
                javax.swing.JOptionPane.INFORMATION_MESSAGE);
        } else {
            txtConsola.append("> [PRUEBA] ❌ Error al cargar el caso de prueba.\n");
            javax.swing.JOptionPane.showMessageDialog(this, 
                "Error al cargar el caso de prueba.\nVerifique el formato del archivo JSON.",
                "Error de Carga",
                javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }        // TODO add your handling code here:
    }//GEN-LAST:event_btnCargarPruebaActionPerformed

    private void btnGuardarEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarEstadoActionPerformed
        // Verificar permisos
    String rolActual = comboUsuario.getSelectedItem().toString();
    if (!rolActual.equalsIgnoreCase("Administrador")) {
        javax.swing.JOptionPane.showMessageDialog(this, 
            "Acceso Denegado: Solo los Administradores pueden guardar el estado.", 
            "Permiso Denegado", 
            javax.swing.JOptionPane.ERROR_MESSAGE);
        txtConsola.append("> [ALERTA] Intento de guardado de estado bloqueado.\n");
        return;
    }
    
    // Actualizar el controlador con los datos actuales antes de guardar
    actualizarControladorDesdeTabla();
    
    // Guardar el estado
    txtConsola.append("> [SISTEMA] Guardando estado actual...\n");
    boolean exito = controladorSistema.guardarEstadoSistema("estado_sistema.json");
    
    if (exito) {
        txtConsola.append("> [SISTEMA] ✅ Estado guardado exitosamente en 'estado_sistema.json'\n");
        javax.swing.JOptionPane.showMessageDialog(this, 
            "Estado guardado exitosamente.\nArchivo: estado_sistema.json", 
            "Guardado Exitoso", 
            javax.swing.JOptionPane.INFORMATION_MESSAGE);
    } else {
        txtConsola.append("> [SISTEMA] ❌ Error al guardar el estado.\n");
        javax.swing.JOptionPane.showMessageDialog(this, 
            "Error al guardar el estado.", 
            "Error", 
            javax.swing.JOptionPane.ERROR_MESSAGE);
    }        // TODO add your handling code here:
    }//GEN-LAST:event_btnGuardarEstadoActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane Peticiones;
    private javax.swing.JTree arbolArchivos;
    private javax.swing.JButton btnCargarPrueba;
    private javax.swing.JButton btnCrear;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardarEstado;
    private javax.swing.JButton btnLeer;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevaCarpeta;
    private javax.swing.JButton btnSimularActionPerformed;
    private javax.swing.JComboBox<String> comboPlanificador;
    private javax.swing.JComboBox<String> comboUsuario;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JList<String> listaJournaling;
    private javax.swing.JList<String> listaProcesos;
    private javax.swing.JPanel panelDisco;
    private javax.swing.JTable tablaAsignacionArchivos;
    private javax.swing.JTable tablaBloqueos;
    private javax.swing.JTextArea txtConsola;
    private javax.swing.JTextField txtPeticiones;
    // End of variables declaration//GEN-END:variables
    // --- MÉTODO PARA DIBUJAR EL DISCO VISUALMENTE ---
// --- MÉTODO PARA DIBUJAR EL DISCO VISUALMENTE ---
    // --- MÉTODO PARA DIBUJAR EL DISCO VISUALMENTE ---
    private void actualizarMapaDisco() {
        if (panelDisco == null || disco == null) return;
        
        panelDisco.removeAll(); 
        
        int totalBloques = disco.getCapacidadTotal();
        int columnas = 10;
        int filas = (int) Math.ceil((double) totalBloques / columnas);
        
        panelDisco.setLayout(new java.awt.GridLayout(filas, columnas, 2, 2)); 
        
        int[] tabla = disco.getTablaAsignacion();
        java.awt.Color[] colores = disco.getColorBloques();
        
        for (int i = 0; i < totalBloques; i++) {
            javax.swing.JLabel cuadrito = new javax.swing.JLabel(String.valueOf(i));
            cuadrito.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            cuadrito.setOpaque(true);
            cuadrito.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.DARK_GRAY));
            
            if (tabla[i] == -2) {
                // LIBRE -> Verde Claro
                cuadrito.setBackground(new java.awt.Color(200, 255, 200)); 
                cuadrito.setForeground(java.awt.Color.BLACK); // Letra negra
            } else {
                // OCUPADO -> Color del archivo
                cuadrito.setBackground(colores[i]);
                cuadrito.setForeground(java.awt.Color.WHITE); // Letra blanca para resaltar
            }
            
            panelDisco.add(cuadrito);
        }
        
        panelDisco.revalidate();
        panelDisco.repaint();
    }
    
    // --- MÉTODO PARA COLOREAR EL CABEZAL EN LA SIMULACIÓN ---
    private void colorearBloque(int numeroBloque, java.awt.Color color) {
        // Buscamos dentro de tu panelDisco
        for (java.awt.Component comp : panelDisco.getComponents()) {
            if (comp instanceof javax.swing.JLabel) {
                javax.swing.JLabel label = (javax.swing.JLabel) comp;
                // Si el texto de la etiqueta coincide con el bloque, lo pintamos
                if (label.getText().equals(String.valueOf(numeroBloque))) {
                    label.setBackground(color);
                    label.setForeground(java.awt.Color.WHITE); // Letra blanca para que resalte
                    label.revalidate();
                    label.repaint();
                    return; // Lo encontramos, dejamos de buscar
                }
            }
        }
    }
    // --- MÉTODO PARA CREAR CARPETAS EN EL ÁRBOL ---
    private void crearNuevaCarpeta(String nombreCarpeta) {
    // 1. Obtenemos el modelo del árbol
    javax.swing.tree.DefaultTreeModel modeloArbol = (javax.swing.tree.DefaultTreeModel) arbolArchivos.getModel();
    
    // 2. Averiguamos dónde crear la carpeta
    javax.swing.tree.DefaultMutableTreeNode nodoSeleccionado = (javax.swing.tree.DefaultMutableTreeNode) arbolArchivos.getLastSelectedPathComponent();
    
    // Si no seleccionó nada, o seleccionó un archivo (que no puede tener hijos), usamos la raíz
    if (nodoSeleccionado == null || !nodoSeleccionado.getAllowsChildren()) {
        nodoSeleccionado = (javax.swing.tree.DefaultMutableTreeNode) modeloArbol.getRoot();
        txtConsola.append("> [INFO] Carpeta creada en la raíz del disco.\n");
    } else {
        txtConsola.append("> [INFO] Carpeta creada dentro de: '" + nodoSeleccionado.toString() + "'.\n");
    }
    
    // 3. Creamos la nueva carpeta con allowsChildren = true
    javax.swing.tree.DefaultMutableTreeNode nuevaCarpeta = new javax.swing.tree.DefaultMutableTreeNode(nombreCarpeta);
    nuevaCarpeta.setAllowsChildren(true);
    
    // 4. Insertamos la carpeta
    modeloArbol.insertNodeInto(nuevaCarpeta, nodoSeleccionado, nodoSeleccionado.getChildCount());
    
    // 5. Expandimos para mostrar la nueva carpeta
    arbolArchivos.scrollPathToVisible(new javax.swing.tree.TreePath(nuevaCarpeta.getPath()));
    arbolArchivos.expandPath(new javax.swing.tree.TreePath(nuevaCarpeta.getPath()));
}
    
    // --- MÉTODO PARA CREAR ARCHIVOS EN EL ÁRBOL ---
// --- MÉTODO PARA CREAR ARCHIVOS EN EL ÁRBOL ---
    private void crearArchivoEnArbol(String nombreArchivo) {
    // 1. Obtenemos el modelo del árbol
    javax.swing.tree.DefaultTreeModel modeloArbol = (javax.swing.tree.DefaultTreeModel) arbolArchivos.getModel();
    
    // 2. Obtenemos el nodo seleccionado
    javax.swing.tree.DefaultMutableTreeNode nodoSeleccionado = (javax.swing.tree.DefaultMutableTreeNode) arbolArchivos.getLastSelectedPathComponent();
    
    // 3. Determinamos dónde crear el archivo
    // Si no hay selección, o seleccionó un archivo (que no puede tener hijos), usamos la raíz
    if (nodoSeleccionado == null || !nodoSeleccionado.getAllowsChildren()) {
        nodoSeleccionado = (javax.swing.tree.DefaultMutableTreeNode) modeloArbol.getRoot();
        txtConsola.append("> [INFO] Archivo creado en la raíz del disco.\n");
    } else {
        txtConsola.append("> [INFO] Archivo creado dentro de la carpeta: '" + nodoSeleccionado.toString() + "'.\n");
    }
    
    // 4. Creamos el nodo del archivo (false = no es carpeta, no puede tener hijos)
    javax.swing.tree.DefaultMutableTreeNode nuevoArchivo = new javax.swing.tree.DefaultMutableTreeNode(nombreArchivo, false);
    
    // 5. Insertamos el archivo en el árbol
    modeloArbol.insertNodeInto(nuevoArchivo, nodoSeleccionado, nodoSeleccionado.getChildCount());
    
    // 6. Expandimos la carpeta para mostrar el archivo recién creado
    arbolArchivos.scrollPathToVisible(new javax.swing.tree.TreePath(nuevoArchivo.getPath()));
    arbolArchivos.expandPath(new javax.swing.tree.TreePath(nodoSeleccionado.getPath()));
    }
    private void actualizarListaJournaling() {
    // Limpiar la lista actual
    javax.swing.DefaultListModel<String> modeloLista = new javax.swing.DefaultListModel<>();
    
    // Verificar si hay registros en el journal
    if (controladorSistema == null || controladorSistema.getBitacora() == null || controladorSistema.getBitacora().getTamaño() == 0) {
        modeloLista.addElement("📔 Bitácora limpia");
        modeloLista.addElement("✅ Sin operaciones pendientes");
    } else {
        // Recorrer la bitácora y agregar los registros
        for (int i = 0; i < controladorSistema.getBitacora().getTamaño(); i++) {
            modelos.RegistroJournal reg = controladorSistema.getBitacora().obtener(i);
            String estadoIcono = "";
            switch (reg.getEstado()) {
                case "PENDIENTE": 
                    estadoIcono = "⏳"; 
                    break;
                case "CONFIRMADA": 
                    estadoIcono = "✅"; 
                    break;
                case "ABORTADA": 
                    estadoIcono = "❌"; 
                    break;
                default: 
                    estadoIcono = "📝";
            }
            modeloLista.addElement(estadoIcono + " [" + reg.getEstado() + "] " + reg.getTipoOperacion() + " → " + reg.getNombreArchivo() + " (" + reg.getBloquesInvolucrados() + " bloques)");
        }
    }
    
    listaJournaling.setModel(modeloLista);
}
    private void actualizarListaProcesos() {
    // Crear un modelo para la lista
    javax.swing.DefaultListModel<String> modeloLista = new javax.swing.DefaultListModel<>();
    
    // Verificar si hay procesos en la cola
    if (controladorSistema == null || controladorSistema.getColaProcesos() == null || controladorSistema.getColaProcesos().estaVacia()) {
        modeloLista.addElement("📭 Cola vacía");
        modeloLista.addElement("⏳ Sin procesos pendientes");
    } else {
        // Recorrer la cola de procesos y agregarlos a la lista
        estructuras.Nodo<modelos.Proceso> actual = controladorSistema.getColaProcesos().getFrente();
        int contador = 1;
        while (actual != null) {
            modelos.Proceso p = actual.getDato();
            
            // Icono según el estado del proceso
            String estadoIcono = "";
            switch (p.getEstado()) {
                case "NUEVO": 
                    estadoIcono = "🆕"; 
                    break;
                case "LISTO": 
                    estadoIcono = "✅"; 
                    break;
                case "EJECUTANDO": 
                    estadoIcono = "⚙️"; 
                    break;
                case "BLOQUEADO": 
                    estadoIcono = "🔒"; 
                    break;
                case "TERMINADO": 
                    estadoIcono = "🏁"; 
                    break;
                default: 
                    estadoIcono = "❓";
            }
            
            // Mostrar información del proceso
            String infoProceso = contador + ". " + estadoIcono + " " + p.toString();
            
            // Si tiene posición asignada, mostrarla
            if (p.getPosicion() != 0) {
                infoProceso += " → Bloque " + p.getPosicion();
            }
            
            modeloLista.addElement(infoProceso);
            actual = actual.getSiguiente();
            contador++;
        }
    }
    
    listaProcesos.setModel(modeloLista);
}
    private void agregarProcesoACola(String tipoOperacion, String nombreArchivo, int bloquesRequeridos, int posicionBloque) {
    if (controladorSistema == null) return;
    
    // Buscar el archivo por nombre
    modelos.Archivo archivoObjetivo = null;
    estructuras.Nodo<modelos.Archivo> actual = controladorSistema.getListaArchivos().getCabeza();
    while (actual != null) {
        if (actual.getDato().getNombre().equals(nombreArchivo)) {
            archivoObjetivo = actual.getDato();
            break;
        }
        actual = actual.getSiguiente();
    }
    
    // Si no se encontró el archivo, crear uno temporal para el proceso
    if (archivoObjetivo == null && !tipoOperacion.equals("CREAR")) {
        archivoObjetivo = new modelos.Archivo(nombreArchivo, bloquesRequeridos, "Sistema", "#FFFFFF");
    }
    
    // Crear el proceso
    modelos.Proceso nuevoProceso = new modelos.Proceso(tipoOperacion, archivoObjetivo, bloquesRequeridos);
    nuevoProceso.setPosicion(posicionBloque);
    
    // Agregar a la cola
    controladorSistema.getColaProcesos().encolar(nuevoProceso);
    
    // Actualizar la vista
    actualizarListaProcesos();
    
    // Registrar en consola
    txtConsola.append("> [COLA] Nuevo proceso agregado: " + nuevoProceso.toString() + " (" + tipoOperacion + " " + nombreArchivo + ")\n");
}
    private void actualizarInterfazDesdeControlador() {
    // 1. Actualizar la tabla de archivos con los archivos cargados
    javax.swing.table.DefaultTableModel modeloTabla = (javax.swing.table.DefaultTableModel) tablaAsignacionArchivos.getModel();
    
    // Limpiar la tabla actual
    modeloTabla.setRowCount(0);
    
    // Recorrer los archivos del controlador y agregarlos a la tabla
    estructuras.Nodo<modelos.Archivo> actualArchivo = controladorSistema.getListaArchivos().getCabeza();
    while (actualArchivo != null) {
        modelos.Archivo archivo = actualArchivo.getDato();
        
        // Asignar espacio en el disco para el archivo (simulación)
        java.awt.Color colorArchivo = disco.generarColorAleatorio();
        int direccionInicial = disco.asignarEspacio(archivo.getTamaño(), colorArchivo);
        
        if (direccionInicial != -1) {
            // Calcular bloque final
            int bloqueFinal = direccionInicial;
            int[] tablaAsignacion = disco.getTablaAsignacion();
            while (tablaAsignacion[bloqueFinal] != -1) {
                bloqueFinal = tablaAsignacion[bloqueFinal];
            }
            
            // Obtener nombre del color
            String nombreColor = obtenerNombreColor(colorArchivo);
            
            modeloTabla.addRow(new Object[]{
                archivo.getNombre(),
                archivo.getTamaño(),
                direccionInicial,
                bloqueFinal,
                nombreColor
            });
            
            // Agregar al árbol
            crearArchivoEnArbol(archivo.getNombre());
        }
        
        actualArchivo = actualArchivo.getSiguiente();
    }
    
    // 2. Actualizar la cola de procesos
    actualizarListaProcesos();
    
    // 3. Actualizar el mapa del disco
    actualizarMapaDisco();
    
    // 4. Registrar en consola los procesos cargados
    txtConsola.append("> [PRUEBA] Procesos en cola:\n");
    estructuras.Nodo<modelos.Proceso> actualProceso = controladorSistema.getColaProcesos().getFrente();
    int contador = 1;
    while (actualProceso != null) {
        modelos.Proceso p = actualProceso.getDato();
        txtConsola.append(">   " + contador + ". " + p.getTipoOperacion() + " en posición " + p.getPosicion() + "\n");
        actualProceso = actualProceso.getSiguiente();
        contador++;
    }
}
    private String obtenerNombreColor(java.awt.Color color) {
    if (color.getRed() == 255 && color.getGreen() == 80 && color.getBlue() == 80) return "Rojo";
    if (color.getRed() == 80 && color.getGreen() == 150 && color.getBlue() == 255) return "Azul";
    if (color.getRed() == 100 && color.getGreen() == 220 && color.getBlue() == 100) return "Verde";
    if (color.getRed() == 255 && color.getGreen() == 200 && color.getBlue() == 50) return "Naranja";
    if (color.getRed() == 180 && color.getGreen() == 100 && color.getBlue() == 255) return "Morado";
    if (color.getRed() == 255 && color.getGreen() == 120 && color.getBlue() == 200) return "Rosa";
    if (color.getRed() == 100 && color.getGreen() == 255 && color.getBlue() == 255) return "Celeste";
    return "Personalizado";
}
    private void ejecutarProcesosCargados() {
    if (controladorSistema == null || controladorSistema.getColaProcesos().estaVacia()) {
        txtConsola.append("> [SIMULACIÓN] No hay procesos en cola para ejecutar.\n");
        return;
    }
    
    // Obtener la política seleccionada
    String algoritmo = comboPlanificador.getSelectedItem().toString();
    planificador.setPolitica(algoritmo);
    planificador.limpiarCola();
    
    // Cargar las solicitudes de la cola de procesos al planificador
    estructuras.Nodo<modelos.Proceso> actual = controladorSistema.getColaProcesos().getFrente();
    while (actual != null) {
        int posicion = actual.getDato().getPosicion();
        planificador.agregarSolicitud(posicion);
        actual = actual.getSiguiente();
    }
    
    txtConsola.append("> [SIMULACIÓN] Ejecutando " + planificador.getColaSolicitudes().getTamaño() + " solicitudes con política " + algoritmo + "\n");
    
    // Cambiar a la pestaña Planificador
    Peticiones.setSelectedIndex(1);
    
    // Ejecutar simulación
    ejecutarSimulacionConSolicitudes();
}
    private void ejecutarSimulacionConSolicitudes() {
    try {
        // Limpiar tabla de simulación
        javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) jTable1.getModel();
        if (modelo.getColumnCount() == 0) {
            String[] columnas = {"Paso", "Bloque Visitado", "Distancia (Costo)"};
            modelo.setColumnIdentifiers(columnas);
        }
        modelo.setRowCount(0);
        
        // Limpiar mapa del disco
        actualizarMapaDisco();
        
        // Obtener el número de solicitudes
        int numSolicitudes = planificador.getColaSolicitudes().getTamaño();
        
        txtConsola.append("> [PLANIFICADOR] Iniciando simulación con " + numSolicitudes + " solicitudes...\n");
        
        // Hilo para animación
        new Thread(() -> {
            try {
                int posAnterior = planificador.getPosicionCabezal();
                int costoTotal = 0;
                
                for (int i = 0; i < numSolicitudes; i++) {
                    Thread.sleep(1000);
                    
                    int posActual = planificador.procesarSiguiente();
                    int costo = Math.abs(posActual - posAnterior);
                    costoTotal += costo;
                    
                    final int paso = i + 1;
                    final int bloqueActual = posActual;
                    final int costoMov = costo;
                    
                    java.awt.EventQueue.invokeLater(() -> {
                        modelo.addRow(new Object[]{paso, bloqueActual, costoMov + " blks"});
                        txtConsola.append("> Paso " + paso + ": Cabezal en bloque " + bloqueActual + " (Costo: " + costoMov + ")\n");
                        colorearBloque(bloqueActual, java.awt.Color.RED);
                    });
                    
                    posAnterior = posActual;
                }
                
                final int totalFinal = costoTotal;
                java.awt.EventQueue.invokeLater(() -> {
                    txtConsola.append("> [OK] Simulación finalizada.\n");
                    txtConsola.append("> 💰 COSTO TOTAL DEL VIAJE: " + totalFinal + " bloques.\n");
                    txtConsola.append("----------------------------------------------------\n");
                });
                
                Thread.sleep(1500);
                java.awt.EventQueue.invokeLater(() -> actualizarMapaDisco());
                
            } catch (Exception e) {
                System.out.println("Error en simulación: " + e.getMessage());
            }
        }).start();
        
    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this, "Error en simulación: " + e.getMessage());
    }
}
    private void cargarEstadoAnterior() {
    String rutaArchivo = "estado_sistema.json";
    java.io.File archivoEstado = new java.io.File(rutaArchivo);
    
    if (archivoEstado.exists()) {
        int respuesta = javax.swing.JOptionPane.showConfirmDialog(this,
            "Se encontró un estado guardado anteriormente.\n¿Desea cargarlo?",
            "Cargar Estado Anterior",
            javax.swing.JOptionPane.YES_NO_OPTION,
            javax.swing.JOptionPane.QUESTION_MESSAGE);
        
        if (respuesta == javax.swing.JOptionPane.YES_OPTION) {
            boolean exito = controladorSistema.cargarEstadoSistema(rutaArchivo);
            if (exito) {
                txtConsola.append("> [SISTEMA] Estado anterior cargado exitosamente.\n");
                
                // Actualizar la tabla de archivos con los datos cargados
                actualizarTablaArchivosDesdeControlador();
                
                // Actualizar el mapa del disco con los archivos cargados
                actualizarMapaDiscoConArchivosCargados();
            } else {
                txtConsola.append("> [ERROR] No se pudo cargar el estado anterior.\n");
            }
        } else {
            txtConsola.append("> [SISTEMA] Iniciando con estado limpio.\n");
        }
    } else {
        txtConsola.append("> [SISTEMA] No se encontró estado guardado. Iniciando con estado limpio.\n");
    }
}
    private void actualizarControladorDesdeTabla() {
    // Limpiar la lista del controlador
    controladorSistema.getListaArchivos().eliminarTodos();
    
    // Recorrer la tabla de archivos y agregarlos al controlador
    javax.swing.table.DefaultTableModel modeloTabla = (javax.swing.table.DefaultTableModel) tablaAsignacionArchivos.getModel();
    
    for (int i = 0; i < modeloTabla.getRowCount(); i++) {
        String nombre = modeloTabla.getValueAt(i, 0).toString();
        int cantidadBloques = Integer.parseInt(modeloTabla.getValueAt(i, 1).toString());
        String color = modeloTabla.getValueAt(i, 4).toString();
        
        modelos.Archivo archivo = new modelos.Archivo(nombre, cantidadBloques, 
            comboUsuario.getSelectedItem().toString(), color);
        controladorSistema.getListaArchivos().agregarAlFinal(archivo);
    }
}
    private void actualizarTablaArchivosDesdeControlador() {
    javax.swing.table.DefaultTableModel modeloTabla = (javax.swing.table.DefaultTableModel) tablaAsignacionArchivos.getModel();
    
    // Configurar encabezados si es necesario
    if (modeloTabla.getColumnCount() == 0 || modeloTabla.getColumnName(0).equals("Title 1")) {
        String[] columnas = {"Nombre del archivo", "Cantidad de Bloques", "Dirección Inicial", "Dirección final", "Color"};
        modeloTabla.setColumnIdentifiers(columnas);
    }
    
    modeloTabla.setRowCount(0);
    
    estructuras.Nodo<modelos.Archivo> actual = controladorSistema.getListaArchivos().getCabeza();
    while (actual != null) {
        modelos.Archivo archivo = actual.getDato();
        
        // Simular asignación de bloques en el disco
        java.awt.Color colorArchivo = disco.generarColorAleatorio();
        int direccionInicial = disco.asignarEspacio(archivo.getTamaño(), colorArchivo);
        
        if (direccionInicial != -1) {
            int bloqueFinal = direccionInicial;
            int[] tablaAsignacion = disco.getTablaAsignacion();
            while (tablaAsignacion[bloqueFinal] != -1) {
                bloqueFinal = tablaAsignacion[bloqueFinal];
            }
            
            String nombreColor = obtenerNombreColor(colorArchivo);
            
            modeloTabla.addRow(new Object[]{
                archivo.getNombre(),
                archivo.getTamaño(),
                direccionInicial,
                bloqueFinal,
                nombreColor
            });
            
            // Agregar al árbol
            crearArchivoEnArbol(archivo.getNombre());
        }
        
        actual = actual.getSiguiente();
    }
}
    private void actualizarMapaDiscoConArchivosCargados() {
    // Primero limpiamos el disco
    for (int i = 0; i < disco.getCapacidadTotal(); i++) {
        disco.getTablaAsignacion()[i] = -2;
        disco.getColorBloques()[i] = null;
    }
    
    // Luego asignamos los archivos
    estructuras.Nodo<modelos.Archivo> actual = controladorSistema.getListaArchivos().getCabeza();
    while (actual != null) {
        modelos.Archivo archivo = actual.getDato();
        java.awt.Color colorArchivo = disco.generarColorAleatorio();
        disco.asignarEspacio(archivo.getTamaño(), colorArchivo);
        actual = actual.getSiguiente();
    }
    
    actualizarMapaDisco();
}
    private void actualizarArchivoEnArbol(String nombreAntiguo, String nombreNuevo) {
    javax.swing.tree.DefaultTreeModel modeloArbol = (javax.swing.tree.DefaultTreeModel) arbolArchivos.getModel();
    javax.swing.tree.DefaultMutableTreeNode raiz = (javax.swing.tree.DefaultMutableTreeNode) modeloArbol.getRoot();
    
    for (int i = 0; i < raiz.getChildCount(); i++) {
        javax.swing.tree.DefaultMutableTreeNode nodo = (javax.swing.tree.DefaultMutableTreeNode) raiz.getChildAt(i);
        
        if (nodo.getUserObject().toString().equals(nombreAntiguo)) {
            nodo.setUserObject(nombreNuevo);
            modeloArbol.nodeChanged(nodo);
            break;
        }
    }
}

private void eliminarArchivoDelArbol(String nombreArchivo) {
    javax.swing.tree.DefaultTreeModel modeloArbol = (javax.swing.tree.DefaultTreeModel) arbolArchivos.getModel();
    javax.swing.tree.DefaultMutableTreeNode raiz = (javax.swing.tree.DefaultMutableTreeNode) modeloArbol.getRoot();
    
    for (int i = 0; i < raiz.getChildCount(); i++) {
        javax.swing.tree.DefaultMutableTreeNode nodo = (javax.swing.tree.DefaultMutableTreeNode) raiz.getChildAt(i);
        
        if (nodo.getUserObject().toString().equals(nombreArchivo)) {
            modeloArbol.removeNodeFromParent(nodo);
            break;
        }
    }
}
    private void aplicarCabezalInicial() {
    try {
        int nuevaPosicion = Integer.parseInt(txtCabezalInicial.getText().trim());
        
        // Validar que esté dentro del rango del disco
        if (nuevaPosicion < 0 || nuevaPosicion >= disco.getCapacidadTotal()) {
            javax.swing.JOptionPane.showMessageDialog(this, 
                "La posición debe estar entre 0 y " + (disco.getCapacidadTotal() - 1), 
                "Error", 
                javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Aplicar la nueva posición al planificador
        planificador.setPosicionCabezal(nuevaPosicion);
        
        txtConsola.append("> [CABEZAL] Posición inicial configurada en: " + nuevaPosicion + "\n");
        
        javax.swing.JOptionPane.showMessageDialog(this, 
            "Cabezal configurado en bloque " + nuevaPosicion, 
            "Cabezal Configurado", 
            javax.swing.JOptionPane.INFORMATION_MESSAGE);
            
    } catch (NumberFormatException e) {
        javax.swing.JOptionPane.showMessageDialog(this, 
            "Ingrese un número válido", 
            "Error", 
            javax.swing.JOptionPane.ERROR_MESSAGE);
    }
}
}
