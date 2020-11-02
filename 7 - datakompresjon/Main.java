import java.io.IOException;

public class Main {
      public static void main(String[] args) throws IOException {
            String original = "src\\original";
            String compressed = "src\\compressed";
            String decompressed = "src\\decompressed";
            DocumentCompressor c = new DocumentCompressor();
            c.compressDocument(original, compressed);

            DocumentDecompressor d = new DocumentDecompressor();
            d.decompressDocument(compressed, decompressed);
      }
}
