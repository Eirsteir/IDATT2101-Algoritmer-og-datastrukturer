import java.io.IOException;

public class DocumentDecompressor {

      public static void main(String[] args) throws IOException {
            String compressed = "src\\compressed";
            String decompressed = "src\\decompressed";

            DocumentDecompressor d = new DocumentDecompressor();
            d.decompressDocument(compressed, decompressed);
      }

      public void decompressDocument(String inPath, String outPath) throws IOException {
            Huffman huffman = new Huffman();
            byte[] decompressedBytes = huffman.decompress(inPath);
            LZ77 lz77 = new LZ77();
            lz77.deCompress(decompressedBytes, outPath);

      }
}
