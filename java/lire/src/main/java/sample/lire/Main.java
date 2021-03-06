package sample.lire;

import net.semanticmetadata.lire.builders.DocumentBuilder;
import net.semanticmetadata.lire.builders.GlobalDocumentBuilder;
import net.semanticmetadata.lire.imageanalysis.features.global.CEDD;
import net.semanticmetadata.lire.searchers.GenericFastImageSearcher;
import net.semanticmetadata.lire.searchers.ImageSearchHits;
import net.semanticmetadata.lire.utils.LuceneUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DecimalFormat;

public class Main {
    public static void main(String[] args) throws IOException {
        deleteIndex();
        updateIndex();
        search();
    }

    private static void deleteIndex() throws IOException {
        try (IndexWriter writer = LuceneUtils.createIndexWriter("index", false, LuceneUtils.AnalyzerType.WhitespaceAnalyzer);
        ) {
            long seqNo = writer.deleteDocuments(new Term(DocumentBuilder.FIELD_NAME_IDENTIFIER, "img/004.jpg"));
            System.out.println("seqNo=" + seqNo);
        }
    }

    private static void updateIndex() throws IOException {
        try (DirectoryReader reader = DirectoryReader.open(FSDirectory.open(Paths.get("index")));
             IndexWriter writer = LuceneUtils.createIndexWriter("index", false, LuceneUtils.AnalyzerType.WhitespaceAnalyzer);
        ) {
            GlobalDocumentBuilder globalDocumentBuilder = new GlobalDocumentBuilder(CEDD.class);

            IndexSearcher indexSearcher = new IndexSearcher(reader);
            DecimalFormat format = new DecimalFormat("000");

            for (int i=1; i<=47; i++) {
                String filePath = "img/" + format.format(i) + ".jpg";
                System.out.println(filePath);

                TopDocs topDocs = indexSearcher.search(new TermQuery(new Term(DocumentBuilder.FIELD_NAME_IDENTIFIER, filePath)), 1);

                if (topDocs.totalHits == 0) {
                    System.out.println("not found index!!");
                    BufferedImage image = ImageIO.read(new File(filePath));
                    Document document = globalDocumentBuilder.createDocument(image, filePath);
                    writer.addDocument(document);
                } else {
                    System.out.println("already exists index!!");
                }
            }
        }
    }

    public static void search() throws IOException {
        try (DirectoryReader reader = DirectoryReader.open(FSDirectory.open(Paths.get("index")));) {
            GenericFastImageSearcher searcher = new GenericFastImageSearcher(5, CEDD.class);

            BufferedImage image = ImageIO.read(new File("img/037.jpg"));

            ImageSearchHits hits = searcher.search(image, reader);

            for (int i=0; i<hits.length(); i++) {
                double score = hits.score(i);
                Document document = reader.document(hits.documentID(i));
                String[] values = document.getValues(DocumentBuilder.FIELD_NAME_IDENTIFIER);
                String filePath = values[0];

                System.out.println(filePath + " score(" + score + ")");
                String name = Paths.get(filePath).toFile().getName();
                Files.copy(Paths.get(filePath), Paths.get("img/result/" + name), StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }

    public static void createIndex() throws IOException {
        GlobalDocumentBuilder globalDocumentBuilder = new GlobalDocumentBuilder(CEDD.class);
        DecimalFormat format = new DecimalFormat("000");

        try (IndexWriter writer = LuceneUtils.createIndexWriter("index", true, LuceneUtils.AnalyzerType.WhitespaceAnalyzer);) {
            for (int i=1; i<=45; i++) {
                String filePath = "img/" + format.format(i) + ".jpg";
                System.out.println(filePath);

                BufferedImage image = ImageIO.read(new File(filePath));
                Document document = globalDocumentBuilder.createDocument(image, filePath);
                writer.addDocument(document);
            }
        }
    }
}
