package com.fcr.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * Classe de métodos utilitários para trabalhar com datas e horas.
 */
public final class DateUtil {

  private static final long MILLISECONDS_PER_SECOND = 1000;
  private static final long MILLISECONDS_PER_MINUTE = MILLISECONDS_PER_SECOND * 60;
  private static final long MILLISECONDS_PER_HOUR = MILLISECONDS_PER_MINUTE * 60;
  private static final long MILLISECONDS_PER_DAY = MILLISECONDS_PER_HOUR * 24;
  private static final long MILLISECONDS_PER_MONTH = MILLISECONDS_PER_DAY * 30;

  public static final String PATTERN_DD_MM_YYYY = "dd/MM/yyyy";
  public static final String PATTERN_DD_MM_SS = "HH:mm:ss";
  public static final String PATTERN_DD_MM_YYYY_HH_MM_SS = "dd/MM/yyyy HH:mm:ss";
  public static final String PATTERN_DD_MM_YYYY_DD_MM_SS = "dd/MM/yyy HH:mm:ss";

  private static Logger logger = Logger.getLogger(DateUtil.class.getName());

  private DateUtil() {
    super();
  }

  /**
   * Retorna a diferença entre as datas em segundos.
   * 
   * @param dateEnd
   *          Início
   * @param dateBegin
   *          Término
   * @return Diferença
   */
  public static long getDiffInSeconds(final Date dateEnd, final Date dateBegin) {
    return DateUtil.getDiffInMilliseconds(dateEnd, dateBegin) / DateUtil.MILLISECONDS_PER_SECOND;
  }

  /**
   * Retorna a diferença entre as datas em minutos.
   * 
   * @param dateEnd
   *          Início
   * @param dateBegin
   *          Término
   * @return Diferença
   */
  public static long getDiffInMinutes(final Date dateEnd, final Date dateBegin) {
    return DateUtil.getDiffInMilliseconds(dateEnd, dateBegin) / DateUtil.MILLISECONDS_PER_MINUTE;
  }

  /**
   * Retorna a diferença entre as datas em horas.
   * 
   * @param dateEnd
   *          Início
   * @param dateBegin
   *          Término
   * @return Diferença
   */
  public static long getDiffInHours(final Date dateEnd, final Date dateBegin) {
    return DateUtil.getDiffInMilliseconds(dateEnd, dateBegin) / DateUtil.MILLISECONDS_PER_HOUR;
  }

  /**
   * Retorna a diferença entre as datas em dias.
   * 
   * @param dateEnd
   *          Início
   * @param dateBegin
   *          Término
   * @return Diferença
   */
  public static long getDiffInDays(final Date dateEnd, final Date dateBegin) {
    return DateUtil.getDiffInMilliseconds(dateEnd, dateBegin) / DateUtil.MILLISECONDS_PER_DAY;
  }

  /**
   * Retorna a diferença entre as datas em meses.
   * 
   * @param dateEnd
   *          Início
   * @param dateBegin
   *          Término
   * @return Diferença
   */
  public static long getDiffInMonths(final Date dateEnd, final Date dateBegin) {
    return DateUtil.getDiffInMilliseconds(dateEnd, dateBegin) / DateUtil.MILLISECONDS_PER_MONTH;
  }

  /**
   * Retorna a diferença entre as datas em miléssimos.
   * 
   * @param dateEnd
   *          Início
   * @param dateBegin
   *          Término
   * @return Diferença
   */
  public static long getDiffInMilliseconds(final Date dateEnd, final Date dateBegin) {

    Calendar end = DateUtil.getToday();
    end.setTime(dateEnd);
    Calendar begin = DateUtil.getToday();
    begin.setTime(dateBegin);

    long endL = end.getTimeInMillis() + end.getTimeZone().getOffset(end.getTimeInMillis());
    long startL = begin.getTimeInMillis() + begin.getTimeZone().getOffset(begin.getTimeInMillis());

    return (endL - startL);
  }

  public static String getSeconds() {

    String seconds;
    final Calendar calendar = Calendar.getInstance();
    int second = calendar.get(Calendar.SECOND);

    seconds = String.valueOf(second);

    return seconds;
  }

  /**
   * Retorna a data de hoje.
   * 
   * @return Data
   */
  public static Calendar getToday() {
    return Calendar.getInstance();
  }

  /**
   * Retorna o ano atual.
   * 
   * @return Ano
   */
  public static Integer getYear() {
    return getToday().get(Calendar.YEAR);
  }

  /**
   * Retorna o ano da data.
   * 
   * @param date
   *          Data
   * @return Ano
   */
  public static Integer getYear(final Date date) {
    Calendar cal = DateUtil.getToday();
    cal.setTime(date);
    return cal.get(Calendar.YEAR);
  }

  /**
   * Retorna o mês atual.
   * 
   * @return Mês
   */
  public static Integer getMonth() {
    return getToday().get(Calendar.MONTH);
  }

  /**
   * Retorna o mês da data.
   * 
   * @param date
   *          Data
   * @return Mês
   */
  public static Integer getMonth(final Date date) {
    Calendar cal = DateUtil.getToday();
    cal.setTime(date);
    return cal.get(Calendar.MONTH) + 1;
  }

  /**
   * Retorna o nome do mês atual.
   * 
   * @return Nome do mês
   */
  public static String getMonthName() {
    return getToday().getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
  }

  /**
   * Retorna o nom do mês da data.
   * 
   * @param date
   *          Data
   * @return Nome do mês
   */
  public static String getMonthName(final Date date) {
    Calendar calendar = Calendar.getInstance(Locale.getDefault());
    calendar.setTime(date);
    return calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
  }

  /**
   * Parse o calendar como String.
   * 
   * @param calendar
   *          Calendar
   * @return Data
   */
  public static String parseCalendarAsString(final Calendar calendar) {
    if (calendar == null) {
      return null;
    }
    return new SimpleDateFormat(PATTERN_DD_MM_YYYY).format(calendar.getTime());
  }

  /**
   * Parse o calendar como String.
   * 
   * @param date
   *          Calendar
   * @param pattern
   *          Pattern
   * @return Data
   */
  public static String parseCalendarAsString(final Calendar calendar, final String pattern) {
    if (calendar == null) {
      return null;
    }
    return new SimpleDateFormat(pattern).format(calendar.getTime());
  }

  /**
   * Parse a data como String.
   * 
   * @param date
   *          Data
   * @return Data
   */
  public static String parseDateAsString(final Date date) {
    if (date == null) {
      return null;
    }
    return new SimpleDateFormat(PATTERN_DD_MM_YYYY).format(date);
  }

  /**
   * Parse a data como String.
   * 
   * @param date
   *          Data
   * @param pattern
   *          Pattern
   * @return Data
   */
  public static String parseDateAsString(final Date date, final String pattern) {
    if (date == null) {
      return null;
    }
    return new SimpleDateFormat(pattern).format(date);
  }

  /**
   * Parse o timestamp como String.
   * 
   * @param timestamp
   *          Timestamp
   * @return Data
   */
  public static String parseTimestampAsString(final Timestamp timestamp) {
    if (timestamp == null) {
      return null;
    }
    return new SimpleDateFormat(PATTERN_DD_MM_YYYY).format(timestamp);
  }

  /**
   * Parse o timestamp como String.
   * 
   * @param timestamp
   *          Timestamp
   * @param pattern
   *          Pattern
   * @return Data
   */
  public static String parseTimestampAsString(final Timestamp timestamp, final String pattern) {
    if (timestamp == null) {
      return null;
    }
    return new SimpleDateFormat(pattern).format(timestamp);
  }

  /**
   * Parse a data como Timestamp.
   * 
   * @param dateAsTimestamp
   *          Data
   * @return Data
   */
  public static Calendar parseTimestamp(final Timestamp dateAsTimestamp) {
    Calendar calendar = getToday();
    calendar.setTimeInMillis(dateAsTimestamp.getTime());
    return calendar;
  }

  /**
   * Parse a data como Calendar.
   * 
   * @param dateAsString
   *          Data String
   * @return Data
   */
  public static Calendar parseDate(final String dateAsString) {
    if (dateAsString == null) {
      return null;
    }
    try {
      Calendar calendar = getToday();
      calendar.setTime(new SimpleDateFormat(PATTERN_DD_MM_YYYY).parse(dateAsString));
      return calendar;
    } catch (ParseException e) {
      logger.severe(e.getMessage());
    }
    return null;
  }

  /**
   * Parse a data como Calendar.
   * 
   * @param dateAsString
   *          Data String
   * @param pattern
   *          Pattern
   * @return Data
   */
  public static Calendar parseDate(final String dateAsString, final String pattern) {
    if (dateAsString == null) {
      return null;
    }
    try {
      Calendar calendar = getToday();
      calendar.setTime(new SimpleDateFormat(pattern).parse(dateAsString));
      return calendar;
    } catch (ParseException e) {
      logger.severe(e.getMessage());
    }
    return null;
  }

  /**
   * Converte as datas em String e verifica se são iguais.
   * 
   * @param date1
   *          Data 1
   * @param date2
   *          Data 2
   * @return Booleano
   */
  public static boolean isDataIgual(final Date date1, final Date date2) {
    String dataString1 = parseDateAsString(date1);
    String dataString2 = parseDateAsString(date2);
    if (dataString1 != null && dataString2 != null) {
      return dataString1.equals(dataString2);
    }
    return false;
  }

  public static Date getDataHoraMinima(Date date) {
    if (date != null) {
      Calendar calendar = Calendar.getInstance();
      calendar.setTimeInMillis(date.getTime());
      calendar.set(Calendar.HOUR_OF_DAY, 0);
      calendar.set(Calendar.MINUTE, 0);
      calendar.set(Calendar.SECOND, 0);
      calendar.set(Calendar.MILLISECOND, 0);
      date.setTime(calendar.getTimeInMillis());
    }
    return date;
  }

  public static Date getDataHoraMaxima(Date date) {
    if (date != null) {
      Calendar calendar = Calendar.getInstance();
      calendar.setTimeInMillis(date.getTime());
      setHoraMaxima(calendar);
      date.setTime(calendar.getTimeInMillis());
    }
    return date;
  }

  public static Calendar getDataHoraMaxima(Calendar date) {
    if (date != null) {
      setHoraMaxima(date);
    }
    return date;
  }

  private static void setHoraMaxima(Calendar calendar) {
    calendar.set(Calendar.HOUR_OF_DAY, 23);
    calendar.set(Calendar.MINUTE, 59);
    calendar.set(Calendar.SECOND, 59);
    calendar.set(Calendar.MILLISECOND, 999);
  }

  /**
   * Adiciona anos a data
   */

  public static Calendar addYear(Calendar data, int ano) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(data.getTime());
    cal.add(Calendar.YEAR, ano);
    return cal;
  }

  /**
   * Adiciona dias a data
   */

  public static Calendar addDay(Calendar data, int day) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(data.getTime());
    cal.add(Calendar.DATE, day);
    return cal;
  }

  /**
   * Verifica se a data informada é maior do que a data atual.
   * 
   * @param data
   * @return Booleano
   */
  public static boolean isDataMaiorQueAtual(Calendar data) {
    return data.after(getToday());
  }
  
  /**
   * Verifica se a data final é menor que a data inicial.
   * 
   * @param dataInicial
   * @param dataB
   * @return Booleano
   */
  public static boolean isDataFinalMenorQueDataInicial(Calendar dataInicial, Calendar dataFinal) {
    return dataInicial.after(dataFinal);
  }

}