package com.prasanna.solutions.square.lunhnyCheck;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LuhnyChecker
{
	private static final String regex = ".*(\\d[ -]*).*";
	private static final Pattern pattern = Pattern.compile(regex);
	private static final String MASK_CHARACTER = "X";

	public static void main(String[] args)
	{
		LuhnyChecker checker = new LuhnyChecker();

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		String inputLine = null;
		try
		{
			while ((inputLine = bufferedReader.readLine()) != null)
			{
				checker.check(inputLine);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private void check(String text)
	{
		List<CreditCardMatch> matches = new ArrayList<CreditCardMatch>();

		Matcher matcher = pattern.matcher(text);

		int numEnd = 0;
		int numStart = 0;

		while (matcher.find())
		{
			String numberInString = matcher.group();

			char[] charArray = numberInString.toCharArray();
			int numberLength = charArray.length;
			int end = numberLength;
			int currentIndex = end;

			while (end > 13)
			{
				int digitCounter = 0;
				int sum = 0;
				String mask = "";
				currentIndex = end;

				while (currentIndex > 0)
				{
					char character = charArray[currentIndex - 1];

					if (Character.isDigit(character))
					{
						int num = Character.getNumericValue(character);

						if (digitCounter % 2 != 0)
						{
							int product = num * 2;

							if (product < 10)
							{
								sum += product;
							}
							else
							{
								while (product > 0)
								{
									int digit = product % 10;
									sum += digit;
									product = product / 10;
								}
							}
						}
						else
						{
							sum += num;
						}

						digitCounter++;

						mask = MASK_CHARACTER + mask;

						if (sum % 10 == 0
						        && ((digitCounter % 16 > 13 && digitCounter % 16 <= 16) || digitCounter % 16 == 0))
						{
							numStart = currentIndex - 1;
							numEnd = end;

							CreditCardMatch match = new CreditCardMatch();
							match.setStart(numStart);
							match.setEnd(numEnd);
							match.setMask(mask);

							CreditCardMatch lastMatch = null;
							if (matches.isEmpty() == false)
							{
								lastMatch = matches.get(matches.size() - 1);
								if (lastMatch.getEnd() == numEnd && lastMatch.getStart() > numStart)
								{
									matches.remove(matches.size() - 1);
								}
							}

							if (matches.contains(match) == false)
							{
								if (lastMatch != null && matches.isEmpty() == false)
								{
									if (lastMatch.getStart() > match.getStart()
									        && lastMatch.getEnd() >= match.getEnd())
									{
										lastMatch = matches.remove(matches.size() - 1);
										mask = lastMatch.getMask();
										for (int i = match.getStart(); i < lastMatch.getStart(); i++)
										{
											mask = MASK_CHARACTER + mask;
										}

										lastMatch.setMask(mask);
										lastMatch.setStart(match.getStart());
										matches.add(lastMatch);
									}
								}
								else
								{
									matches.add(match);
								}
							}

						}
						if (digitCounter % 16 == 0)
						{
							digitCounter = 0;
							mask = "";
							sum = 0;
							break;
						}
					}
					else
					{
						mask = character + mask;
					}

					currentIndex--;
				}

				if (matches.isEmpty() == false)
				{
					if (matches.get(matches.size() - 1).getStart() == 0)
					{
						break;
					}
				}
				end--;
			}
		}

		String rep = text;
		for (CreditCardMatch match : matches)
		{
			rep = rep.replace(text.substring(match.getStart(), match.getEnd()),
			        match.getMask());
		}

		System.out.println(rep);
	}
}
