/*
 * 
 *   * 
 */
package lab3;

import java.util.List;

/**
 *
 * @author Carlos Cofré <carlos.cofre@usach.cl>
 */
public class Git {
    public static Repositorio init(String nombre, Usuario usuario){
        Workspace workspace = new Workspace("Workspace");
        Index index = new Index("Index");
        LocalRepository localRepository = new LocalRepository("Local Repository");
        RemoteRepository remoteRepository = new RemoteRepository("Remote Repository");     
        
        return new Repositorio(nombre, usuario, workspace, index, localRepository, remoteRepository);
    }
    
    
    public static Repositorio add(Repositorio repositorio, List<String> nombresArchivos){
        Workspace workspace = repositorio.getWorkspace();
        LocalRepository localRepository = repositorio.getLocalRepository();
        
        int i, nArchivosWS = workspace.getArchivos().size();
        for (String nombre : nombresArchivos){ 
            for(i=0; i<nArchivosWS; i++){
                if(workspace.getArchivos().get(i).getNombre().equals(nombre)){
                    if( (workspace.getArchivos().get(i)).reemplazable(localRepository.getArchivo(nombre)))
                        repositorio.getIndex().addArchivo(nombre);
                }
            }
        }
        return repositorio;
    }

    public static Repositorio commit(Repositorio repositorio, Usuario usuario, String comentario){
        
        Index index = repositorio.getIndex();
        Workspace workspace = repositorio.getWorkspace();
        LocalRepository localRepository = repositorio.getLocalRepository();
        
        List<String> modificadosIndex = index.getModified();
                    
        Commit commit = new Commit(usuario, comentario, workspace.getArchivos(modificadosIndex));
        localRepository.addCommit(commit);
        System.out.println(index.getModified().toString());
        repositorio.copiarEntre(index.getModified(), workspace, localRepository);
        for(int i=0; i<modificadosIndex.size(); i++)
            index.getArchivos().replace(modificadosIndex.get(i), index.COMMITED);
        return repositorio;
        
    }
    
}