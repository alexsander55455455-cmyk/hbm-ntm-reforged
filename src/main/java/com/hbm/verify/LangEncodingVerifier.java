package com.hbm.verify;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Loads lang files from the mod classpath the same way Minecraft reads packaged assets.
 * Runnable via Gradle task {@code verifyLangEncoding}.
 */
public final class LangEncodingVerifier {

	private static final Pattern MOJIBAKE = Pattern.compile("РЈ|РЅ|СЃ|Рё|Р°|╨|╤");
	private static final Pattern CYRILLIC = Pattern.compile("[а-яА-ЯёЁіїєґІЇЄҐ]");
	private static final Pattern DRILLBIT_KEY = Pattern.compile("^item\\.drillbit_.*\\.name=");
	private static final byte[] URANIUM_UTF8 = "Урановый слиток".getBytes(StandardCharsets.UTF_8);

	private LangEncodingVerifier() {
	}

	public static void main(String[] args) throws IOException {
		Path scratch = Paths.get(System.getenv().getOrDefault(
				"GOAL_SCRATCH",
				"C:/Temp/grok-goal-52eec85734e0/implementer"));
		Files.createDirectories(scratch);

		LangReport ru = verifyLang("ru_ru.lang", "item.ingot_uranium.name", "Уранов", 100_000);
		LangReport uk = verifyLang("uk_ua.lang", "item.ingot_uranium.name", "Уранов", 50_000);

		Path out = scratch.resolve("lang-encoding-execution-java.txt");
		StringBuilder sb = new StringBuilder();
		sb.append("source=LangEncodingVerifier.main\n");
		sb.append("classpath=/assets/hbm/lang/*.lang\n\n");
		appendReport(sb, ru);
		appendReport(sb, uk);
		Files.write(out, sb.toString().getBytes(StandardCharsets.UTF_8));

		System.out.println("lang_ru_ru bom=" + ru.bom + " cyrillic=" + ru.cyrillicCount + " mojibake=" + ru.mojibakeCount);
		System.out.println("lang_ru_ru sample=" + ru.sampleValue);
		System.out.println("lang_uk_ua bom=" + uk.bom + " cyrillic=" + uk.cyrillicCount + " mojibake=" + uk.mojibakeCount);
		System.out.println("lang_uk_ua sample=" + uk.sampleValue);
		System.out.println("LangEncodingVerifier PASS");
	}

	private static void appendReport(StringBuilder sb, LangReport report) {
		sb.append(report.file).append('\n');
		sb.append("  bom=").append(report.bom).append('\n');
		sb.append("  cyrillic=").append(report.cyrillicCount).append('\n');
		sb.append("  mojibake=").append(report.mojibakeCount).append('\n');
		sb.append("  drillbit_bad=").append(report.drillbitBad).append('\n');
		sb.append("  sample_key=").append(report.sampleKey).append('\n');
		sb.append("  sample_value=").append(report.sampleValue).append('\n');
		sb.append("  sample_utf8=").append(Arrays.toString(report.sampleUtf8)).append('\n');
		sb.append("  pass=").append(report.pass).append("\n\n");
	}

	private static LangReport verifyLang(String file, String sampleKey, String sampleNeedle, int minCyrillic) throws IOException {
		String resource = "/assets/hbm/lang/" + file;
		InputStream stream = LangEncodingVerifier.class.getResourceAsStream(resource);
		if (stream == null) {
			throw new AssertionError("missing classpath resource " + resource);
		}

		byte[] raw = readAll(stream);
		boolean bom = raw.length >= 3 && raw[0] == (byte) 0xEF && raw[1] == (byte) 0xBB && raw[2] == (byte) 0xBF;
		int offset = bom ? 3 : 0;
		String text = new String(raw, offset, raw.length - offset, StandardCharsets.UTF_8);

		int mojibakeCount = countMatches(MOJIBAKE, text);
		int cyrillicCount = countMatches(CYRILLIC, text);
		int drillbitBad = 0;
		String sampleValue = null;
		byte[] sampleUtf8 = new byte[0];
		for (String line : text.split("\n")) {
			if (line.startsWith(sampleKey + "=")) {
				sampleValue = line.substring(sampleKey.length() + 1).trim();
				sampleUtf8 = sampleValue.getBytes(StandardCharsets.UTF_8);
			}
			if (file.equals("uk_ua.lang") && DRILLBIT_KEY.matcher(line).find()) {
				String value = line.substring(line.indexOf('=') + 1).trim();
				if (value.contains("╨") || value.contains("╤") || !CYRILLIC.matcher(value).find()) {
					drillbitBad++;
				}
			}
		}

		boolean pass = !bom
				&& mojibakeCount == 0
				&& drillbitBad == 0
				&& cyrillicCount > minCyrillic
				&& sampleValue != null
				&& sampleValue.contains(sampleNeedle)
				&& (!file.equals("ru_ru.lang") || Arrays.equals(sampleUtf8, URANIUM_UTF8));

		if (!pass) {
			throw new AssertionError("lang verification failed for " + file);
		}

		LangReport report = new LangReport();
		report.file = file;
		report.bom = bom;
		report.mojibakeCount = mojibakeCount;
		report.drillbitBad = drillbitBad;
		report.cyrillicCount = cyrillicCount;
		report.sampleKey = sampleKey;
		report.sampleValue = sampleValue;
		report.sampleUtf8 = sampleUtf8;
		report.pass = true;
		return report;
	}

	private static int countMatches(Pattern pattern, String text) {
		Matcher matcher = pattern.matcher(text);
		int count = 0;
		while (matcher.find()) {
			count++;
		}
		return count;
	}

	private static byte[] readAll(InputStream stream) throws IOException {
		byte[] buffer = new byte[8192];
		int read;
		java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
		while ((read = stream.read(buffer)) != -1) {
			out.write(buffer, 0, read);
		}
		return out.toByteArray();
	}

	private static final class LangReport {
		private String file;
		private boolean bom;
		private int mojibakeCount;
		private int drillbitBad;
		private int cyrillicCount;
		private String sampleKey;
		private String sampleValue;
		private byte[] sampleUtf8;
		private boolean pass;
	}
}