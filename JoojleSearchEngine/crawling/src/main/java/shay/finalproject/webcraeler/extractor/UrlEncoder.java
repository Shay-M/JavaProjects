package shay.finalproject.webcraeler.extractor;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class UrlEncoder {
    public static String encodeUrl(final String url) {
        String encodedPath = URLEncoder.encode(url, StandardCharsets.UTF_8)
                .replaceAll("\\s+", "%20");
        final int fragmentIndex = encodedPath.indexOf('#');
        if (fragmentIndex != -1) {
            final String fragment = encodedPath.substring(fragmentIndex + 1);
            final String encodedFragment = URLEncoder.encode(fragment);
            return encodedPath.substring(0, fragmentIndex + 1) + encodedFragment;
        }
        else {
            return encodedPath;
        }
    }
}


// private String normalizeURL(String url) {
//     try {
//         URL parsedUrl = new URL(url);
//         String protocol = parsedUrl.getProtocol().toLowerCase();
//         String host = parsedUrl.getHost().toLowerCase();
//         String path = parsedUrl.getPath();
//
//         if (!protocol.equals("http") && !protocol.equals("https")) {
//             return null;
//         }
//
//         if (host.startsWith("www.")) {
//             host = host.substring(4);
//         }
//
//         if (path.equals("") || path.equals("/")) {
//             path = "/index.html";
//         }
//
//         return protocol + "://" + host + path;
//     } catch (Exception e) {
//         return null;
//     }
// }