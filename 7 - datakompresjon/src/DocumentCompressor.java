import java.io.IOException;

public class DocumentCompressor {

      public static void main(String[] args) throws IOException {
            String original = "src\\original";
            String compressed = "src\\compressed";

            DocumentCompressor c = new DocumentCompressor();
            c.compressDocument(original, compressed);
      }

      public void compressDocument(String path, String outpath) throws IOException {
            LZ77 lz77 = new LZ77();
            byte[] compressedBytes = lz77.compress(path);

            Huffman huffman = new Huffman();
            huffman.compress(compressedBytes, outpath);
      }
}
