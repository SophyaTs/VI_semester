﻿using System;
using System.Collections.Generic;

namespace Lab9_5
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("started\n");
            var test=new Test("./resources/index.html");
            test.AddTask(2000);
            test.Run();
        }
    }
}