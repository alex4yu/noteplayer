	package notePlayer;
import java.util.*;
import core.MidiWrapper;
import java.util.concurrent.TimeUnit;

public class NotePlayer
{
    public static void main(String[] args)
    {
    	// This is where you will begin writing your code, and this is where the program will start.
    	// Although you can place all of your code here in main, we strongly suggest that you
    	// separate your code into multiple helper methods.  Your main method should then call those
    	// helper methods at the right places.  Organizing your code like this makes your code easier
    	// to read and debug, and helps avoid duplicating code.  
    	
    	String user="";
    	Scanner input = new Scanner(System.in);
    	user = input.nextLine();
    	while (!user.equals("quit"))
    	{
	    	
	    	if (user.indexOf("set instrument")!=-1)
	    	{
	    		int instrument=Integer.parseInt(user.substring(15));
	    		setInstrument(instrument);
	    	}
	    	else if (user.indexOf("list instruments")!=-1)
	    	{
	    		System.out.println("0: Piano 1        1: Piano 2        2: Piano 3        3: Honky-tonk     4: E.Piano 1\n5: E.Piano 2      6: Harpsichord    7: Clav.          8: Celesta        9: Glockenspiel\n10: Music Box     11: Vibraphone    12: Marimba       13: Xylophone     14: Tubular-bell\n15: Santur        16: Organ 1       17: Organ 2       18: Organ 3       19: Church Org.1");
	    		System.out.println("20: Reed Organ    21: Accordion Fr  22: Harmonica     23: Bandoneon     24: Nylon-str.Gt\n25: Steel-str.Gt  26: Jazz Gt.      27: Clean Gt.     28: Muted Gt.     29: Overdrive Gt\n30: DistortionGt  31: Gt.Harmonics  32: Acoustic Bs.  33: Fingered Bs.  34: Picked Bs.");
	    		System.out.println("35: Fretless Bs.  36: Slap Bass 1   37: Slap Bass 2   38: Synth Bass 1  39: Synth Bass 2\n40: Violin        41: Viola         42: Cello         43: Contrabass    44: Tremolo Str\n45: PizzicatoStr  46: Harp          47: Timpani       48: Strings       49: Slow Strings\n50: Syn.Strings1  51: Syn.Strings2  52: Choir Aahs    53: Voice Oohs    54: SynVox\n55: OrchestraHit  56: Trumpet       57: Trombone      58: Tuba          59: MutedTrumpet\n60: French Horns  61: Brass 1       62: Synth Brass1  63: Synth Brass2  64: Soprano Sax\n65: Alto Sax      66: Tenor Sax     67: Baritone Sax  68: Oboe          69: English Horn\n70: Bassoon       71: Clarinet      72: Piccolo       73: Flute         74: Recorder\n75: Pan Flute     76: Bottle Blow   77: Shakuhachi    78: Whistle       79: Ocarina");
	    		System.out.println("80: Square Wave   81: Saw Wave      82: Syn.Calliope  83: Chiffer Lead  84: Charang\n85: Solo Vox      86: 5th Saw Wave  87: Bass & Lead   88: Fantasia      89: Warm Pad\n90: Polysynth     91: Space Voice   92: Bowed Glass   93: Metal Pad     94: Halo Pad\n95: Sweep Pad     96: Ice Rain      97: Soundtrack    98: Crystal       99: Atmosphere\n100: Brightness   101: Goblin       102: Echo Drops   103: Star Theme   104: Sitar\n105: Banjo        106: Shamisen     107: Koto         108: Kalimba      109: Bagpipe\n110: Fiddle       111: Shanai       112: Tinkle Bell  113: Agogo        114: Steel Drums\n115: Woodblock    116: Taiko        117: Melo. Tom 1  118: Synth Drum   119: Reverse Cym.\n120: Gt.FretNoise 121: Breath Noise 122: Seashore     123: Bird         124: Telephone 1\n125: Helicopter   126: Applause     127: Gun Shot");
	    	}
	    	else
	    	{
	    		int transpose=0;
	    		double tempo=1;
	    		String notelist;
	    		if (user.indexOf(",")!=-1)
	    		{
	    			String adjust=user.substring(0,user.indexOf(","));
	    			notelist=user.substring(user.indexOf(",")+1);
	    			transpose=transposecalc(adjust.substring(0,adjust.indexOf("_")));
	    			tempo=Double.parseDouble(adjust.substring(adjust.indexOf("_")+1));
	    		}
	    		else
	    		{
	    			notelist=user;
	    		}
		    	String [] notes = notelist.split(" ");
		    	int length = notes.length;
		    	
		    	for (int i=0; i<=length-1; i++)
		    	{
		    		//String letter = notes[i].substring(0,notes[i].indexOf("_"));
		        	//String duration = notes[i].substring(notes[i].indexOf("_")+1);
		    		int numnote=0;
		    		for (int j=0; j<notes[i].length()-1; j++)
		    		{
		    			String normal= notes[i];
		    			String lower = notes[i].toLowerCase();
		    			
		    			if (!(lower.substring(j,j+1).equals(normal.substring(j,j+1))))
		    			{
		    				numnote+=1;
		    			}
		    		}
		    		if (numnote==1)
		    		{    			
			        	
			        	
			        	int note=noteCalc(notes[i]);
			        	note=note+transpose;
			        	String duration = notes[i].substring(notes[i].indexOf("_")+1);
			        	int durint = (int)(Integer.parseInt(duration)*tempo);
			        	if (note == 0)
			        	{
			        		
			        		try
							{
								Thread.sleep(durint);
							}
							catch (InterruptedException e)
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
			        	}
			        	else
			        	{
			        		playNote(note, durint);
			        	}
			        	
		    		}
		    		else
		    		{
		    			chordCalc(notes[i],numnote, transpose, tempo);
		    		}
		    	}
	    	}
	    	
	    	user = input.nextLine();
    	}
    	    	
    }
    public static int letterToMIDINote(String nletter)
    {
    	int MIDI=0;
    	if (nletter.equals("C"))
    	{
    		MIDI=60;
    	}
    	else if (nletter.equals("D"))
    	{
    		MIDI=62;
    	}
    	else if (nletter.equals("E"))
    	{
    		MIDI=64;
    	}
    	else if (nletter.equals("F"))
    	{
    		MIDI=65;
    	}
    	else if (nletter.equals("G"))
    	{
    		MIDI=67;
    	}
    	else if (nletter.equals("A"))
    	{
    		MIDI=69;
    	}
    	else if (nletter.equals("B"))
    	{
    		MIDI=71;
    	}
    	return MIDI;
    }
    public static int octavecalc(String a)
    {
    	int r=0;
    	if (a.length()==2)
    	{
    		int num=Integer.parseInt(a.substring(1));
    		r=num*-12;
    	}
    	else if (a.length()==1)
    	{
    		r=12*Integer.parseInt(a.substring(0));
    	}
    	else
    	{
    		r=0;
    	}
    	return r;
    }
    public static int transposecalc(String a)
    {
    	int r=0;
    	if (a.indexOf("-")!=-1)
    	{
    		int num=Integer.parseInt(a.substring(1));
    		r=num*-1;
    	}
    	else 
    	{
    		r=Integer.parseInt(a.substring(0));
    	}
    	return r;
    }
    public static void chordCalc(String chord,int num, int transpose, double tempo)
    {
    	int substart=0;
    	int subend=0;
    	String lower = chord.toLowerCase();
    	String duration = chord.substring(chord.indexOf("_")+1);
    	int durint = (int)(Integer.parseInt(duration)*tempo);
    	int intnote=0;
    	int intnote1=0;
    	int intnote2=0;
    	int intnote3=0;
    	int intnote4=0;
    	int count=1;
    	for (int j=1; j<chord.length()-1; j++)
		{	
				
			if (!(lower.substring(j,j+1).equals(chord.substring(j,j+1))))
			{
				subend=j;
				String note=chord.substring(substart,subend);
				note+="_";
				intnote=noteCalc(note);
				intnote=intnote+transpose;
	        	
	        	if (count==1)
	        	{
	        		intnote1=intnote;
	        	}
	        	else if (count==2)
	        	{
	        		intnote2=intnote;
	        	}
	        	else if (count==3)
	        	{
	        		intnote3=intnote;
	        	}
	        	else if (count==4)
	        	{
	        		intnote4=intnote;
	        	}
	        	substart=j;
	        	count++;
			}
			
		}
    	
    	String note=chord.substring(substart,chord.indexOf("_"));
		note+="_";
		intnote=noteCalc(note);
		intnote=intnote+transpose;
		
    	if (count==2)
    	{
    		intnote2=intnote;
    	}
    	else if (count==3)
    	{
    		intnote3=intnote;
    	}
    	else if (count==4)
    	{
    		intnote4=intnote;
    	}
    	if (num==2)
    	{
    		core.MidiWrapper.playTwoNoteChord(intnote1,intnote2,durint);
    	}
    	else if (num==3)
    	{
    		core.MidiWrapper.playThreeNoteChord(intnote1,intnote2,intnote3,durint);
    	}
    	else if (num==4)
    	{
    		core.MidiWrapper.playFourNoteChord(intnote1,intnote2,intnote3, intnote4, durint);
    	}
    }
    public static int noteCalc(String s)
    {
    	int r=0;
    	String letter = s.substring(0,1);
    	int accidental=0;
    	int octave=0;
    	int underscore=s.indexOf("_");
    	if (s.indexOf("#")!=-1)
    	{
    		accidental = 1;
    		octave=octavecalc(s.substring(2,underscore));
    	}
    	else if(s.indexOf("r")!= -1)
    	{
    		return 0;
    	}
    	else if(s.indexOf("b")!=-1)
    	{
    		accidental = -1;
    		octave = octavecalc(s.substring(2,underscore));
    	}
    	else
    	{
    		octave=octavecalc(s.substring(1,underscore));
    	}
    	
    	int note = letterToMIDINote(letter);
    	r=note+accidental+octave;
    	return r;
    }
    // You may choose to add extra helper methods here to break up your code into
    // smaller, reusable chunks
    
    
    
    
    
    
    
    
    
    
    
	//                      .d"""" """$$$$be.
	//                    -"           ^""**$$$e.
	//                  ."                   '$$$c
	//                 /                      "4$$b
	//                d  3                      $$$$
	//                $  *                   .$$$$$$
	//               .$  ^c           $$$$$e$$$$$$$$.
	//               d$L  4.         4$$$$$$$$$$$$$$b
	//               $$$$b ^ceeeee.  4$$ECL.F*$$$$$$$
	//   e$""=.      $$$$P d$$$$F $ $$$$$$$$$- $$$$$$
	//  z$$b. ^c     3$$$F "$$$$b   $"$$$$$$$  $$$$*"      .=""$c
	// 4$$$$L        $$P"  "$$b   .$ $$$$$...e$$        .=  e$$$.
	// ^*$$$$$c  %..   *c    ..    $$ 3$$$$$$$$$$eF     zP  d$$$$$
	//   "**$$$ec   "   %ce""    $$$  $$$$$$$$$$*    .r" =$$$$P""
	//         "*$b.  "c  *$e.    *** d$$$$$"L$$    .d"  e$$***"
	//           ^*$$c ^$c $$$      4J$$$$$% $$$ .e*".eeP"
	//              "$$$$$$"'$=e....$*$$**$cz$$" "..d$*"
	//                "*$$$  *=%4.$ L L$ P3$$$F $$$P"
	//                   "$   "%*ebJLzb$e$$$$$b $P"
	//                     %..      4$$$$$$$$$$ "
	//                      $$$e   z$$$$$$$$$$%
	//                       "*$c  "$$$$$$$P"
	//                        ."""*$$$$$$$$bc
	//                     .-"    .$***$$$"""*e.
	//                  .-"    .e$"     "*$c  ^*b.
	//           .=*""""    .e$*"          "*bc  "*$e..
	//         .$"        .z*"               ^*$e.   "*****e.
	//         $$ee$c   .d"                     "*$.        3.
	//         ^*$E")$..$"                         *   .ee==d%
	//            $.d$$$*                           *  J$$$e*
	//             """""                              "$$$"
	
	// WWWWWWWW                           WWWWWWWW                                                      iiii                                        !!!  !!!  !!! 
	// W::::::W                           W::::::W                                                     i::::i                                      !!:!!!!:!!!!:!!
	// W::::::W                           W::::::W                                                      iiii                                       !:::!!:::!!:::!
	// W::::::W                           W::::::W                                                                                                 !:::!!:::!!:::!
	//  W:::::W           WWWWW           W:::::Waaaaaaaaaaaaa  rrrrr   rrrrrrrrr   nnnn  nnnnnnnn    iiiiiiinnnn  nnnnnnnn       ggggggggg   ggggg!:::!!:::!!:::!
	//   W:::::W         W:::::W         W:::::W a::::::::::::a r::::rrr:::::::::r  n:::nn::::::::nn  i:::::in:::nn::::::::nn    g:::::::::ggg::::g!:::!!:::!!:::!
	//    W:::::W       W:::::::W       W:::::W  aaaaaaaaa:::::ar:::::::::::::::::r n::::::::::::::nn  i::::in::::::::::::::nn  g:::::::::::::::::g!:::!!:::!!:::!
	//     W:::::W     W:::::::::W     W:::::W            a::::arr::::::rrrrr::::::rnn:::::::::::::::n i::::inn:::::::::::::::ng::::::ggggg::::::gg!:::!!:::!!:::!
	//      W:::::W   W:::::W:::::W   W:::::W      aaaaaaa:::::a r:::::r     r:::::r  n:::::nnnn:::::n i::::i  n:::::nnnn:::::ng:::::g     g:::::g !:::!!:::!!:::!
	//       W:::::W W:::::W W:::::W W:::::W     aa::::::::::::a r:::::r     rrrrrrr  n::::n    n::::n i::::i  n::::n    n::::ng:::::g     g:::::g !:::!!:::!!:::!
	//        W:::::W:::::W   W:::::W:::::W     a::::aaaa::::::a r:::::r              n::::n    n::::n i::::i  n::::n    n::::ng:::::g     g:::::g !!:!!!!:!!!!:!!
	//         W:::::::::W     W:::::::::W     a::::a    a:::::a r:::::r              n::::n    n::::n i::::i  n::::n    n::::ng::::::g    g:::::g  !!!  !!!  !!! 
	//          W:::::::W       W:::::::W      a::::a    a:::::a r:::::r              n::::n    n::::ni::::::i n::::n    n::::ng:::::::ggggg:::::g                
	//           W:::::W         W:::::W       a:::::aaaa::::::a r:::::r              n::::n    n::::ni::::::i n::::n    n::::n g::::::::::::::::g  !!!  !!!  !!! 
	//            W:::W           W:::W         a::::::::::aa:::ar:::::r              n::::n    n::::ni::::::i n::::n    n::::n  gg::::::::::::::g !!:!!!!:!!!!:!!
	//             WWW             WWW           aaaaaaaaaa  aaaarrrrrrr              nnnnnn    nnnnnniiiiiiii nnnnnn    nnnnnn    gggggggg::::::g  !!!  !!!  !!! 
	//                                                                                                                                     g:::::g                
	//                                                                                                                         gggggg      g:::::g                
	//                                                                                                                         g:::::gg   gg:::::g                
	//                                                                                                                          g::::::ggg:::::::g                
	//                                                                                                                           gg:::::::::::::g                 
	//                                                                                                                             ggg::::::ggg                   
	//                                                                                                                                gggggg                         
    
    // WARNING!!!!  You will CALL these methods, but you MUST NOT MODIFY any
    // of the code below
    

    /**
     * WARNING!!!  DO NOT MODIFY THIS METHOD.
     * 
     * Once you have calculated the MIDI note number and its duration, call this
     * method to play that note.
     * 
     * @param noteNumber The MIDI note number, as described in the spec.
     * @param durationMs The number of milliseconds to play the note.  A larger number will play the note for a longer time.
     */
    public static void playNote(int noteNumber, int durationMs)
    {
    	// WARNING!!!  DO NOT MODIFY THIS METHOD.
        MidiWrapper.playNote(noteNumber, durationMs);
    }
    
    
    
    
	/**
	 * WARNING!!!  DO NOT MODIFY THIS METHOD.
	 * 
	 * Call this method to change the instrument used to play notes.
	 * 
	 * @param instrumentNumber The MIDI instrument number to begin using.  Must
	 * be in the range between 0 and 127 inclusive.
	 */
	public static void setInstrument(int instrumentNumber)
	{
		// WARNING!!!  DO NOT MODIFY THIS METHOD.
		MidiWrapper.setInstrument(instrumentNumber);
	}
    
}
