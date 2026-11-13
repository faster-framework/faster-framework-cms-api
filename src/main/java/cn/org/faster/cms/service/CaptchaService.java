package cn.org.faster.cms.service;

import cn.org.faster.cms.config.CaptchaProperties;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 图形验证码：进程内存储、一次性使用、带过期时间。
 */
@Service
public class CaptchaService {

    private static final String ALPHABET = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";
    private static final int WIDTH = 120;
    private static final int HEIGHT = 40;
    private static final SecureRandom RANDOM = new SecureRandom();

    private final Map<String, Entry> store = new ConcurrentHashMap<>();
    private final CaptchaProperties properties;

    public CaptchaService(CaptchaProperties properties) {
        this.properties = properties;
    }

    public record Challenge(String token, String image) {
    }

    private record Entry(String code, Instant expiresAt) {
    }

    public Challenge issue() {
        purge();
        String code = randomCode(properties.length());
        String token = java.util.UUID.randomUUID().toString();
        store.put(token, new Entry(code, Instant.now().plus(properties.ttl())));
        return new Challenge(token, "data:image/png;base64," + Base64.getEncoder().encodeToString(render(code)));
    }

    /**
     * 校验并立即失效。
     */
    public boolean verify(String token, String input) {
        if (token == null || input == null) {
            return false;
        }
        Entry entry = store.remove(token);
        if (entry == null || entry.expiresAt().isBefore(Instant.now())) {
            return false;
        }
        return entry.code().equalsIgnoreCase(input.trim());
    }

    private void purge() {
        Instant now = Instant.now();
        store.entrySet().removeIf(entry -> entry.getValue().expiresAt().isBefore(now));
    }

    private String randomCode(int length) {
        StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            builder.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return builder.toString();
    }

    private byte[] render(String code) {
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        try {
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics.setColor(new Color(245, 247, 250));
            graphics.fillRect(0, 0, WIDTH, HEIGHT);

            graphics.setStroke(new BasicStroke(1.2f));
            for (int i = 0; i < 6; i++) {
                graphics.setColor(randomColor(150, 220));
                graphics.drawLine(RANDOM.nextInt(WIDTH), RANDOM.nextInt(HEIGHT),
                        RANDOM.nextInt(WIDTH), RANDOM.nextInt(HEIGHT));
            }

            graphics.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 26));
            int step = WIDTH / (code.length() + 1);
            for (int i = 0; i < code.length(); i++) {
                graphics.setColor(randomColor(20, 120));
                graphics.drawString(String.valueOf(code.charAt(i)), step * (i + 1) - 8, 29);
            }
        } finally {
            graphics.dispose();
        }
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            ImageIO.write(image, "png", out);
            return out.toByteArray();
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    private Color randomColor(int min, int max) {
        int bound = max - min;
        return new Color(min + RANDOM.nextInt(bound), min + RANDOM.nextInt(bound), min + RANDOM.nextInt(bound));
    }
}
