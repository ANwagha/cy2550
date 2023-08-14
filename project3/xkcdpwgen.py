#!/usr/bin/env python3

import argparse
import random

# Load the wordlist from the words.txt file
with open("words.txt", "r") as f:
    wordlist = [line.strip() for line in f]

def generate_password(num_words, num_caps, num_numbers, num_symbols):
    password_words = random.sample(wordlist, num_words)
    password = "".join(password_words)

    # Capitalize random words
    for _ in range(num_caps):
        word_index = random.randint(0, num_words - 1)
        password = password[:word_index] + password[word_index].capitalize() + password[word_index + 1:]

    # Insert random numbers
    for _ in range(num_numbers):
        position = random.randint(0, len(password))
        password = password[:position] + str(random.randint(0, 9)) + password[position:]

    # Insert random symbols
    symbols = "~!@#$%^&*.:;"
    for _ in range(num_symbols):
        position = random.randint(0, len(password))
        symbol = random.choice(symbols)
        password = password[:position] + symbol + password[position:]

    return password

def main():
    parser = argparse.ArgumentParser(description="Generate a secure, memorable password using the XKCD method")
    parser.add_argument("-w", "--words", type=int, default=4, help="include WORDS words in the password (default=4)")
    parser.add_argument("-c", "--caps", type=int, default=0, help="capitalize the first letter of CAPS random words (default=0)")
    parser.add_argument("-n", "--numbers", type=int, default=0, help="insert NUMBERS random numbers in the password (default=0)")
    parser.add_argument("-s", "--symbols", type=int, default=0, help="insert SYMBOLS random symbols in the password (default=0)")
    args = parser.parse_args()

    if args.words <= 0:
        print("Number of words must be greater than 0.")
        return

    if args.caps < 0:
        print("Number of capitalized words cannot be negative.")
        return

    if args.numbers < 0:
        print("Number of numbers cannot be negative.")
        return

    if args.symbols < 0:
        print("Number of symbols cannot be negative.")
        return

    if args.words > len(wordlist):
        print("Not enough words in the wordlist.")
        return

    password = generate_password(args.words, args.caps, args.numbers, args.symbols)
    print(password)

if __name__ == "__main__":
    main()

