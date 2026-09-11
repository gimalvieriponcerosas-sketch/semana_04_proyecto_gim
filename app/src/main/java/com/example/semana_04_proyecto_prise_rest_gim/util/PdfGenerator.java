package com.example.semana_04_proyecto_prise_rest_gim.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import com.example.semana_04_proyecto_prise_rest_gim.R;
import com.example.semana_04_proyecto_prise_rest_gim.data.model.*;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class PdfGenerator {

    public static String generateActa(Context context, Fiscalizacion fiscalizacion, Establecimiento establecimiento,
                                     List<Precio> precios, List<Verificacion> verificaciones,
                                     List<HechoVerificado> hechos, List<Firma> firmas) {
        try {
            String fileName = "Acta_" + fiscalizacion.getExpediente() + ".pdf";
            File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), fileName);
            PdfWriter writer = new PdfWriter(new FileOutputStream(file));
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf, PageSize.A4);
            document.setMargins(30, 30, 30, 30);

            // --- PAGINA 1 ---
            addHeader(document, context, fiscalizacion.getExpediente());
            addTitle(document);
            addAgentInfo(document, establecimiento, fiscalizacion);
            addPreciosTable(document, precios);
            addVerificaciones(document, verificaciones);
            addHechosVerificados(document, hechos, 1, 3);
            addFooter(document, 1, fiscalizacion.getExpediente());
            
            document.add(new AreaBreak());

            // --- PAGINA 2 ---
            addHeader(document, context, fiscalizacion.getExpediente());
            addHechosVerificados(document, hechos, 4, 6);
            addOtrosSection(document);
            addFirmas(document, firmas, fiscalizacion);
            addBaseLegal(document);
            addFooter(document, 2, fiscalizacion.getExpediente());

            document.close();
            return file.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void addHeader(Document document, Context context, String expediente) {
        Table headerTable = new Table(UnitValue.createPercentArray(new float[]{3, 2}));
        headerTable.setWidth(UnitValue.createPercentValue(100));

        // LADO IZQUIERDO: Logo y Dirección
        Cell leftCell = new Cell().setBorder(Border.NO_BORDER);
        int resId = context.getResources().getIdentifier("img", "drawable", context.getPackageName());
        if (resId != 0) {
            try {
                Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), resId);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                Image img = new Image(ImageDataFactory.create(stream.toByteArray()));
                img.setWidth(150);
                leftCell.add(img);
            } catch (Exception e) {
                leftCell.add(new Paragraph("OSINERGMIN").setBold().setFontSize(14));
            }
        } else {
            leftCell.add(new Paragraph("OSINERGMIN").setBold().setFontSize(14).setFontColor(ColorConstants.BLUE));
        }
        leftCell.add(new Paragraph("Oficina Regional Huánuco\nDirección: Pasaje Mayro No 121\nTeléfono: 062 - 518499").setFontSize(7).setMarginTop(5));
        headerTable.addCell(leftCell);

        // LADO DERECHO: Caja Expediente
        Cell rightCell = new Cell().setVerticalAlignment(VerticalAlignment.MIDDLE);
        Table expBox = new Table(1).setWidth(UnitValue.createPercentValue(100));
        expBox.addCell(new Cell().add(new Paragraph("EXPEDIENTE Nro.").setFontSize(9)).setBorder(Border.NO_BORDER));
        expBox.addCell(new Cell().add(new Paragraph(expediente).setBold().setFontSize(12)).setPadding(5));
        rightCell.add(expBox).setBorder(Border.NO_BORDER).setPaddingLeft(20);
        headerTable.addCell(rightCell);

        document.add(headerTable);
    }

    private static void addTitle(Document document) {
        document.add(new Paragraph("Acta de Fiscalización del Cumplimiento del Procedimiento de Entrega de Información de Precios de Combustibles Derivados de Hidrocarburos PRICE")
                .setTextAlignment(TextAlignment.CENTER)
                .setBold()
                .setFontSize(10)
                .setMarginTop(10)
                .setUnderline());
    }

    private static void addAgentInfo(Document document, Establecimiento est, Fiscalizacion fis) {
        Table table = new Table(UnitValue.createPercentArray(new float[]{1, 1, 1}));
        table.setWidth(UnitValue.createPercentValue(100)).setFontSize(7).setMarginTop(10);

        table.addCell(new Cell(1, 3).add(new Paragraph("AGENTE FISCALIZADO: " + est.getNombre().toUpperCase())));
        
        table.addCell(new Cell().add(new Paragraph("CÓDIGO OSINERGMIN: " + est.getCodigoOsinergmin())));
        table.addCell(new Cell(1, 2).add(new Paragraph("REGISTRO HIDROCARBUROS N: " + est.getRegistroHidrocarburos())));
        
        table.addCell(new Cell().add(new Paragraph("FECHA DE DILIGENCIA: " + fis.getFechaDiligencia())));
        table.addCell(new Cell().add(new Paragraph("HORA DE APERTURA: " + fis.getHoraApertura())));
        table.addCell(new Cell().add(new Paragraph("HORA DE CIERRE: " + fis.getHoraCierre())));

        table.addCell(new Cell(1, 3).add(new Paragraph("DIRECCIÓN: " + est.getDireccion().toUpperCase())));
        
        table.addCell(new Cell().add(new Paragraph("DISTRITO: " + est.getDistrito().toUpperCase())));
        table.addCell(new Cell().add(new Paragraph("PROVINCIA: " + est.getProvincia().toUpperCase())));
        table.addCell(new Cell().add(new Paragraph("DEPARTAMENTO: HUANUCO").setBold().setFontColor(ColorConstants.BLUE)));

        table.addCell(new Cell(1, 2).add(new Paragraph("RUC/DNI: " + est.getRuc())));
        table.addCell(new Cell().add(new Paragraph("TELÉFONO/FAX: " + est.getTelefono())));
        
        table.addCell(new Cell(1, 3).add(new Paragraph("FISCALIZADOR DE OSINERGMIN: " + fis.getFiscalizadorResponsable().toUpperCase()).setBold()));

        document.add(table);
    }

    private static void addPreciosTable(Document document, List<Precio> precios) {
        document.add(new Paragraph("I. INFORMACIÓN RECABADA:").setBold().setFontSize(8).setMarginTop(5).setUnderline());

        // Tabla con estructura compleja (Anidada)
        Table mainTable = new Table(UnitValue.createPercentArray(new float[]{2, 1, 1, 1, 1, 1, 0.5f, 1, 0.3f, 0.3f, 0.3f, 0.3f, 0.3f}));
        mainTable.setWidth(UnitValue.createPercentValue(100)).setFontSize(6);

        // Encabezados
        mainTable.addHeaderCell(new Cell(2, 1).add(new Paragraph("Producto Fiscalizado")).setBackgroundColor(ColorConstants.LIGHT_GRAY).setTextAlignment(TextAlignment.CENTER));
        mainTable.addHeaderCell(new Cell(2, 1).add(new Paragraph("Diesel B5 /\nDiesel B5 S-50\n(galón)")).setTextAlignment(TextAlignment.CENTER));
        mainTable.addHeaderCell(new Cell(2, 1).add(new Paragraph("G-84 /\nGasohol 84 Plus\n(galón)")).setTextAlignment(TextAlignment.CENTER));
        mainTable.addHeaderCell(new Cell(2, 1).add(new Paragraph("Gasolina\nRegular / Gasohol\nRegular (galón)")).setTextAlignment(TextAlignment.CENTER));
        mainTable.addHeaderCell(new Cell(2, 1).add(new Paragraph("Gasolina\nPremium / Gasohol\nPremium\n(galón)")).setTextAlignment(TextAlignment.CENTER));
        mainTable.addHeaderCell(new Cell(2, 1).add(new Paragraph("GLP\nAutomotor\n(galón)")).setTextAlignment(TextAlignment.CENTER));
        mainTable.addHeaderCell(new Cell(2, 1).add(new Paragraph("Otros")).setTextAlignment(TextAlignment.CENTER));
        mainTable.addHeaderCell(new Cell(2, 1).add(new Paragraph("Marca")).setTextAlignment(TextAlignment.CENTER));
        mainTable.addHeaderCell(new Cell(1, 5).add(new Paragraph("GLP Envasado en Cilindros - Kilogramos:")).setTextAlignment(TextAlignment.CENTER));
        
        mainTable.addHeaderCell(new Cell().add(new Paragraph("3")).setTextAlignment(TextAlignment.CENTER));
        mainTable.addHeaderCell(new Cell().add(new Paragraph("5")).setTextAlignment(TextAlignment.CENTER));
        mainTable.addHeaderCell(new Cell().add(new Paragraph("10")).setTextAlignment(TextAlignment.CENTER));
        mainTable.addHeaderCell(new Cell().add(new Paragraph("15")).setTextAlignment(TextAlignment.CENTER));
        mainTable.addHeaderCell(new Cell().add(new Paragraph("45")).setTextAlignment(TextAlignment.CENTER));

        // Filas de datos
        String[] labels = {"Precio registrado en PRICE", "Precio publicado en establec.", "Precio consignado en surtidor", "Precio con descuento PRICE"};
        for (String label : labels) {
            mainTable.addCell(new Cell().add(new Paragraph(label)));
            for (int i = 0; i < 12; i++) mainTable.addCell(new Cell().add(new Paragraph("")));
        }

        document.add(mainTable);
    }

    private static void addVerificaciones(Document document, List<Verificacion> verifs) {
        Table table = new Table(UnitValue.createPercentArray(new float[]{4, 1, 1, 4, 1, 1, 4, 1, 1}));
        table.setWidth(UnitValue.createPercentValue(100)).setFontSize(7).setMarginTop(5);

        String[] questions = {"¿Teléfono publicado?", "¿Teléfono en PRICE?", "¿Horario publicado?"};
        for (int i = 0; i < 3; i++) {
            table.addCell(new Cell().add(new Paragraph(questions[i] + " ***")));
            boolean res = (i < verifs.size()) && verifs.get(i).isRespuesta();
            table.addCell(new Cell().add(new Paragraph("Si")).setBackgroundColor(res ? ColorConstants.YELLOW : ColorConstants.WHITE).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(new Paragraph("No")).setBackgroundColor(!res ? ColorConstants.YELLOW : ColorConstants.WHITE).setTextAlignment(TextAlignment.CENTER));
        }
        document.add(table);
        document.add(new Paragraph("* Para el caso de Productores... ** Aplica a Productores... *** Aplica a Plantas Envasadoras...").setFontSize(5));
    }

    private static void addHechosVerificados(Document document, List<HechoVerificado> hechos, int start, int end) {
        if (start == 1) document.add(new Paragraph("II. HECHOS VERIFICADOS:").setBold().setFontSize(8).setMarginTop(5).setUnderline().setTextAlignment(TextAlignment.CENTER));

        Table table = new Table(UnitValue.createPercentArray(new float[]{1, 10, 10}));
        table.setWidth(UnitValue.createPercentValue(100)).setFontSize(7);
        
        table.addHeaderCell(new Cell().add(new Paragraph("N°")).setBackgroundColor(ColorConstants.LIGHT_GRAY).setTextAlignment(TextAlignment.CENTER));
        table.addHeaderCell(new Cell().add(new Paragraph("INCUMPLIMIENTO")).setBackgroundColor(ColorConstants.LIGHT_GRAY).setTextAlignment(TextAlignment.CENTER));
        table.addHeaderCell(new Cell().add(new Paragraph("HECHOS VERIFICADOS")).setBackgroundColor(ColorConstants.LIGHT_GRAY).setTextAlignment(TextAlignment.CENTER));

        for (int i = start; i <= end; i++) {
            table.addCell(new Cell().add(new Paragraph(String.valueOf(i))).setTextAlignment(TextAlignment.CENTER));
            String desc = "Incumplimiento " + i + ": No registra ni actualiza... Base Legal: Art...";
            table.addCell(new Cell().add(new Paragraph(desc)));
            
            String hechoText = "";
            for (HechoVerificado h : hechos) {
                if (h.getIncumplimientoId() == i) hechoText = h.getDescripcionHecho();
            }
            table.addCell(new Cell().add(new Paragraph(hechoText)));
        }
        document.add(table);
        
        if (start == 1) {
            document.add(new Paragraph("ÍTEMS ADICIONALES PARA PLANTAS ENVASADORAS, LOCALES DE VENTA, GRIFOS...").setBold().setFontSize(7).setTextAlignment(TextAlignment.CENTER).setBorder(new SolidBorder(0.5f)));
        }
    }

    private static void addOtrosSection(Document document) {
        document.add(new Paragraph("III. OTROS:").setBold().setFontSize(8).setMarginTop(5).setUnderline().setTextAlignment(TextAlignment.CENTER));
        Table table = new Table(1).setWidth(UnitValue.createPercentValue(100)).setFontSize(7);
        table.addCell(new Cell().add(new Paragraph("Otras ocurrencias detectadas en la fiscalización:\n\n")));
        table.addCell(new Cell().add(new Paragraph("Documentación recabada en la fiscalización:\n\n")));
        table.addCell(new Cell().add(new Paragraph("Manifestaciones u observaciones del Agente Fiscalizado:\n\n")));
        table.addCell(new Cell().add(new Paragraph("Negativa del Agente Fiscalizado o demás participantes a identificarse, suscribir o recibir el acta:\n\n")));
        document.add(table);
    }

    private static void addFirmas(Document document, List<Firma> firmas, Fiscalizacion fis) {
        Table table = new Table(2).setWidth(UnitValue.createPercentValue(100)).setMarginTop(30).setFontSize(7);
        
        String nombreRecibe = "";
        String dniRecibe = "";
        String relacionRecibe = "";

        for (Firma f : firmas) {
            if ("RECIBE".equals(f.getTipoFirma())) {
                nombreRecibe = f.getNombre();
                dniRecibe = f.getDni();
                relacionRecibe = f.getRelacion();
            }
        }

        // Firma Fiscalizador (Izquierda)
        Cell fCell = new Cell().setBorder(Border.NO_BORDER);
        fCell.add(new Paragraph("......................................................................").setTextAlignment(TextAlignment.CENTER));
        fCell.add(new Paragraph("Firma del Fiscalizador de Osinergmin").setBold());
        fCell.add(new Paragraph("DNI: " + "46060749")); 
        fCell.add(new Paragraph("Apellidos y nombres:\n" + fis.getFiscalizadorResponsable().toUpperCase()));
        table.addCell(fCell);

        // Firma Recibe (Derecha)
        Cell rCell = new Cell().setBorder(Border.NO_BORDER);
        rCell.add(new Paragraph("......................................................................").setTextAlignment(TextAlignment.CENTER));
        rCell.add(new Paragraph("Firma de quien recibe").setBold());
        rCell.add(new Paragraph("DNI: " + dniRecibe));
        rCell.add(new Paragraph("Apellidos y nombres: " + nombreRecibe.toUpperCase()));
        rCell.add(new Paragraph("\nRelación con Agente Fiscalizado: " + relacionRecibe));
        table.addCell(rCell);

        document.add(table);
    }

    private static void addBaseLegal(Document document) {
        document.add(new Paragraph("Base Legal: Texto Único Ordenado de la Ley N° 27444, Ley del Procedimiento Administrativo General, aprobado por Decreto Supremo Nº 004-2019-JUS; Ley de Creación de Osinergmin...")
                .setFontSize(5).setTextAlignment(TextAlignment.JUSTIFIED).setMarginTop(20));
    }

    private static void addFooter(Document document, int page, String expediente) {
        Table footer = new Table(1).setWidth(UnitValue.createPercentValue(100)).setMarginTop(10);
        footer.addCell(new Cell().add(new Paragraph("PARA TRÁMITES POSTERIORES REFERENTES A ESTA FISCALIZACIÓN, SEÑALAR EL NÚMERO DE EXPEDIENTE: " + expediente).setFontSize(7)).setPadding(5));
        document.add(footer);
        document.add(new Paragraph("Página " + page + " de 2").setTextAlignment(TextAlignment.CENTER).setFontSize(8).setBold());
    }
}
