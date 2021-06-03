#define DOCTEST_CONFIG_IMPLEMENT_WITH_MAIN

#include "doctest.h"
#include "MD4.h"

TEST_CASE("Empty string") {
    auto res = MD4("", 0);
    std::string digest = res;
    REQUIRE(digest == "31d6cfe0d16ae931b73c59d7e0c089c0");
}

TEST_CASE("a") {
    auto res = MD4("a", 1);
    std::string digest = res;
    REQUIRE(digest == "bde52cb31de33e46245e05fbdbd6fb24");
}

TEST_CASE("abc") {
    auto res = MD4("abc", 3);
    std::string digest = res;
    REQUIRE(digest == "a448017aaf21d8525fc10ae87aa6729d");
}

TEST_CASE("message digest") {
    auto res = MD4("message digest", 14);
    std::string digest = res;
    REQUIRE(digest == "d9130a8164549fe818874806e1c7014b");
}

TEST_CASE("abcdefghijklmnopqrstuvwxyz") {
    auto res = MD4("abcdefghijklmnopqrstuvwxyz", 26);
    std::string digest = res;
    REQUIRE(digest == "d79e1c308aa5bbcdeea8ed63df412da9");
}

TEST_CASE("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789") {
    auto res = MD4("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789", 62);
    std::string digest = res;
    REQUIRE(digest == "043f8582f241db351ce627e153e7f0e4");
}

TEST_CASE("12345678901234567890123456789012345678901234567890123456789012345678901234567890") {
    auto res = MD4("12345678901234567890123456789012345678901234567890123456789012345678901234567890", 80);
    std::string digest = res;
    REQUIRE(digest == "e33b4ddc9c38f2199c3e7b164fcc0536");
}